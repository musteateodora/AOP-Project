package com.student.app.service;

import com.student.app.exceptions.MaxParticipantsException;
import com.student.app.exceptions.StudentNotFoundException;
import com.student.app.model.Course;
import com.student.app.model.Student;
import com.student.app.model.dto.CourseDTO;
import com.student.app.repository.StudentRepository;
import javassist.NotFoundException;
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
        if (course.getParticipants() + 1 <= course.getMaxParticipants()) {
            student.getCourses().add(course);
            studentRepository.save(student);
            course.setParticipants(course.getParticipants() + 1);
            courseService.updateCourse(course);
        } else {
            throw new MaxParticipantsException("Sorry, you are unable to enroll on this course. We reached the maximum number of participants. ");
        }
    }

    @Transactional
    public void leaveCourse(String phoneNumber, Long courseId) {
        Student student = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        student.getCourses().remove(course);
        studentRepository.save(student);
        course.setParticipants(course.getParticipants() - 1);
        courseService.updateCourse(course);
    }

    public Student identifyStudent(String phoneNumber) {
        return studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }

    public List<CourseDTO> getAllCourses(String phoneNumber) {
        List<CourseDTO> courses = new ArrayList<>();
        Student student = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Iterator<Course> courseIterator = student.getCourses().iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            courses.add(courseService.mapCourseDAOtoDTO(course));

        }
        return courses;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(String phoneNumber) throws NotFoundException {
        Student studentToDelete = studentRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new StudentNotFoundException("Student not found"));
        studentRepository.delete(studentToDelete);
    }


}
