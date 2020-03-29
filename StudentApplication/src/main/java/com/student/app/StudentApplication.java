package com.student.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StudentApplication {
    public static final String APP_URL = "http://localhost:8090/";

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);

    }
}
