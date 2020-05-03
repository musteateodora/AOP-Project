package com.student.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void executionPointCut() {
    }

    @Pointcut("within(com.student.app..*)"
            + " || within(com.student.app.service..*)"
            + " || within(com.student.app.controller..*)")
    public void beansPointCut() {
    }

    @Around("executionPointCut() && beansPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering {}.{}() with arguments = {}", joinPoint.getSignature().getDeclaringTypeName()
                    , joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }

        try {
            Object result = joinPoint.proceed();
            if (logger.isDebugEnabled()) {
                logger.debug("Exiting {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw ex;
        }
    }

    @AfterThrowing(pointcut = "executionPointCut() && beansPointCut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logger.error("Exception in {}.{}(), with cause {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                throwable.getCause() != null ? throwable.getCause() : "null cause");
    }

    @Around("@annotation(ExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.debug("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }
}
