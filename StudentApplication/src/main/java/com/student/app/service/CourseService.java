package com.student.app.service;

import com.student.app.exceptions.CourseNotFoundException;
import com.student.app.model.Course;
import com.student.app.model.Teacher;
import com.student.app.model.dto.CourseDto;
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

    public CourseDto saveCourse(CourseDto courseDto) throws NotFoundException {
        Course course = mapCourseDTOtoDAO(courseDto);
        Teacher teacher = teacherService.getTeacher(courseDto.getTeacherPhoneNumber());
        course.setTeacher(teacher);
        courseRepository.save(course);
        return mapCourseDAOtoDTO(course);
    }

    public CourseDto updateCourse(Course course) {
        return mapCourseDAOtoDTO(courseRepository.save(course));
    }

    public List<CourseDto> getAllCourses(Optional<String> domain) {
        List<CourseDto> courses = new ArrayList<>();
        Iterator<Course> courseIterator;
        if (domain.isPresent()) {
            courseIterator = courseRepository.findAllByDomain(domain).iterator();
        } else {
            courseIterator = courseRepository.findAll().iterator();
        }
        List<CourseDto> coursesDTO = new ArrayList<>();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            courses.add(mapCourseDAOtoDTO(course));

        }
        return courses;
    }

    Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found"));
    }


    public Course mapCourseDTOtoDAO(CourseDto courseDTO) {
        return new Course(null, courseDTO.getName(), courseDTO.getDetails(), courseDTO.getLocation(),
                courseDTO.getDateTime(), courseDTO.getDuration(), courseDTO.getMaxParticipants(),
                courseDTO.getDomain(), 0, null, null);
    }

    public CourseDto mapCourseDAOtoDTO(Course course) {
        return new CourseDto(course.getId(), course.getName(), course.getDetails(), course.getLocation(),
                course.getDateTime(), course.getDuration(), course.getMaxParticipants(), course.getDomain(),
                course.getTeacher().getPhoneNumber(), course.getParticipants());
    }


}
