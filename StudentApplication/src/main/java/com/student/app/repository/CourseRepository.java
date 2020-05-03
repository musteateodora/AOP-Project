package com.student.app.repository;

import com.student.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByDomain(Optional<String> domain);

    List<Course> findAllByTeacher_PhoneNumber(String phoneNumber);

}
