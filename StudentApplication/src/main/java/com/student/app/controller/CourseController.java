package com.student.app.controller;

import com.student.app.aspect.ExecutionTime;
import com.student.app.model.Course;
import com.student.app.model.dto.CourseDTO;
import com.student.app.service.CourseService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ExecutionTime
    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) throws NotFoundException {
        CourseDTO newCourse = courseService.saveCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);

    }

    @ExecutionTime
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourse(@RequestParam Optional<String> domain) {
        return ResponseEntity.ok(courseService.getAllCourses(domain));
    }

    @ExecutionTime
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<CourseDTO>> getAllcoursesByTeacher(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(courseService.getAllCoursesByTeacher(phoneNumber));
    }

    @ExecutionTime
    @PutMapping
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @ExecutionTime
    @DeleteMapping("/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

}
