package com.student.app.service;

import com.student.app.exceptions.TeacherNotFoundException;
import com.student.app.model.Teacher;
import com.student.app.repository.TeacherRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher getTeacher(String phoneNumber) throws NotFoundException {
        return teacherRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(String phoneNumber) throws NotFoundException {
        Teacher teacherToDelete = teacherRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new TeacherNotFoundException("Teacher not found"));
        teacherRepository.delete(teacherToDelete);
    }
}
