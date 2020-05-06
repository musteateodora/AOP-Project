package com.student.app.aspect;

import com.student.app.model.Teacher;
import com.student.app.repository.TeacherRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void executionPointCut() {
    }

    @Autowired
    private TeacherRepository teacherRepository;

    @Pointcut("within(com.student.app.service..*)"
            + " || within(com.student.app.controller..*)")
    public void beansPointCut() {
    }

    @Around("executionPointCut() && beansPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Entering {}.{}() with arguments = {}", joinPoint.getSignature().getDeclaringTypeName()
                , joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        logger.debug("Exiting {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        return result;

    }

    @Around("@annotation(ExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        logger.debug("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    @AfterThrowing(pointcut = "executionPointCut() && beansPointCut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logger.error("Exception in {}.{}(), with cause {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                throwable.getMessage());
    }

    @Pointcut("execution(*  org.springframework.data.jpa.repository.JpaRepository.save(..))")
    public void afterSave() {
    }

    @AfterReturning(pointcut = "afterSave()")
    public void saveCreatedTime(JoinPoint joinPoint) throws Throwable {
        logger.debug("New {} created at {}", joinPoint.getArgs(), formatter.format(new Date(System.currentTimeMillis())));
    }

    @Pointcut("execution(* com.student.app.service.TeacherService.saveTeacher(..))")
    public void saveTeacher() {
    }


    @Around("saveTeacher()")
    public Teacher setCreatedTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Teacher teacher = (Teacher) proceedingJoinPoint.proceed();
        teacher.setCreatedTime(new Date(System.currentTimeMillis()));
        teacherRepository.save(teacher);
        logger.debug("Created new teacher {}", teacher.toString());

        return teacher;

    }
}
