package com.student.app.repository;

import com.student.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByPhoneNumber(String phoneNumber);
}
