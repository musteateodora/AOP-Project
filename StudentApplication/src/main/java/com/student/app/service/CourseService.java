package com.student.app.service;

import com.student.app.exceptions.CourseNotFoundException;
import com.student.app.model.Course;
import com.student.app.model.Teacher;
import com.student.app.model.dto.CourseDTO;
import com.student.app.repository.CourseRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private TeacherService teacherService;

    public CourseService(CourseRepository courseRepository, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
    }

    public CourseDTO saveCourse(CourseDTO courseDto) throws NotFoundException {
        Course course = mapCourseDTOtoDAO(courseDto);
        Teacher teacher = teacherService.getTeacher(courseDto.getTeacherPhoneNumber());
        course.setTeacher(teacher);
        courseRepository.save(course);
        return mapCourseDAOtoDTO(course);
    }

    public CourseDTO updateCourse(CourseDTO courseDto) throws NotFoundException {
        Course course = mapCourseDTOtoDAO(courseDto);
        Teacher teacher = teacherService.getTeacher(courseDto.getTeacherPhoneNumber());
        course.setTeacher(teacher);
        courseRepository.save(course);
        return mapCourseDAOtoDTO(course);
    }

    public List<CourseDTO> getAllCourses(Optional<String> domain) {
        List<CourseDTO> courses = new ArrayList<>();
        Iterator<Course> courseIterator;
        if (domain.isPresent()) {
            courseIterator = courseRepository.findAllByDomain(domain).iterator();
        } else {
            courseIterator = courseRepository.findAll().iterator();
        }
        List<CourseDTO> coursesDTO = new ArrayList<>();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            courses.add(mapCourseDAOtoDTO(course));

        }
        return courses;
    }

    public List<CourseDTO> getAllCoursesByTeacher(String phoneNumber) {
        List<CourseDTO> courses = new ArrayList<>();
        List<Course> courseList = courseRepository.findAllByTeacher_PhoneNumber(phoneNumber);
        if (null == courseList || courseList.isEmpty()) {
            throw new CourseNotFoundException("Courses not found.");
        }

        for (Course course : courseList) {
            courses.add(mapCourseDAOtoDTO(course));
        }
        return courses;
    }

    Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found"));
    }


    public void deleteCourse(Long courseId) {
        Course courseToDelete = courseRepository.findById(courseId).orElseThrow(() ->
                new CourseNotFoundException("Course not found"));
        courseRepository.delete(courseToDelete);
    }


    public Course mapCourseDTOtoDAO(CourseDTO courseDTO) {
        return new Course(null, courseDTO.getName(), courseDTO.getDetails(), courseDTO.getLocation(),
                courseDTO.getDuration(), courseDTO.getMaxParticipants(),
                courseDTO.getDomain(), 0, null, null);
    }

    public CourseDTO mapCourseDAOtoDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDetails(), course.getLocation(),
                course.getDuration(), course.getMaxParticipants(), course.getDomain(),
                course.getTeacher().getPhoneNumber(), course.getParticipants());
    }


}
