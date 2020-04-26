package com.student.app.controller;

import com.student.app.model.Student;
import com.student.app.model.dto.CourseDTO;
import com.student.app.service.StudentService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(student));
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Student> getStudent(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(studentService.identifyStudent(phoneNumber));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity deleteStudent(@PathVariable String phoneNumber) throws NotFoundException {
        studentService.deleteStudent(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{phoneNumber}/courses/{courseId}")
    public ResponseEntity attendCourse(@PathVariable String phoneNumber, @PathVariable Long courseId) {
        studentService.enroll(phoneNumber, courseId);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{phoneNumber}/courses/{courseId}")
    public ResponseEntity leaveCourse(@PathVariable String phoneNumber, @PathVariable Long courseId) {
        studentService.leaveCourse(phoneNumber, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{phoneNumber}/courses")
    public ResponseEntity<List<CourseDTO>> getAllCoursesByStudent(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(studentService.getAllCourses(phoneNumber));
    }

}
