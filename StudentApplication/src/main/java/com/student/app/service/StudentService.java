package com.student.app.service;

import com.student.app.exceptions.StudentNotFoundException;
import com.student.app.model.Course;
import com.student.app.model.Student;
import com.student.app.model.dto.CourseDto;
import com.student.app.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void enroll(String phoneNumber, Long courseId) {
        Student student = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    @Transactional
    public void leaveCourse(String phoneNumber, Long courseId) {
        Student student = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    public Student identifyStudent(String phoneNumber) {
        return studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }

    public List<CourseDto> getAllCourses(String phoneNumber) {
        List<CourseDto> courses = new ArrayList<>();
        Student student = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));

        Iterator<Course> courseIterator = student.getCourses().iterator();
        List<CourseDto> coursesDTO = new ArrayList<>();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            courses.add(courseService.mapCourseDAOtoDTO(course));

        }
        return courses;
    }


}
