package com.student.app.controller;

import com.student.app.aspect.ExecutionTime;
import com.student.app.model.Teacher;
import com.student.app.service.TeacherService;
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
@RequestMapping(value = "/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ExecutionTime
    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        Teacher newTeacher = teacherService.saveTeacher(teacher);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
    }

    @ExecutionTime
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable String phoneNumber) throws NotFoundException {
        return ResponseEntity.ok(teacherService.getTeacher(phoneNumber));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @PutMapping
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.updateTeacher(teacher));
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity deleteTeacher(@PathVariable String phoneNumber) throws NotFoundException {
        teacherService.deleteTeacher(phoneNumber);
        return ResponseEntity.ok().build();
    }


}
