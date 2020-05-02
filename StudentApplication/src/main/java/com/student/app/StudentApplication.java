package com.student.app;

import com.student.app.model.Course;
import com.student.app.model.Student;
import com.student.app.model.Teacher;
import com.student.app.repository.CourseRepository;
import com.student.app.repository.StudentRepository;
import com.student.app.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StudentApplication implements CommandLineRunner {
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private CourseRepository courseRepository;

    public StudentApplication(StudentRepository studentRepository, TeacherRepository teacherRepository,
                              CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        Teacher firstTeacher = new Teacher("0712121212", "Popescu", "Ion", "popescu.ion@gmail.com");
        teacherRepository.save(firstTeacher);

        Teacher secondTeacher = new Teacher("0713131313", "Andreescu", "Olivia", "olivia.andreescu@gmail.com");
        teacherRepository.save(secondTeacher);

        Course firstCourse = Course.builder()
                .name("Tehnici de programare")
                .details("Cursul de tehnici de programare pentru anul II")
                .location("Amfiteatrul Haret")
                .domain("Facultatea de Matematica si Informatica")
                .duration(2)
                .participants(0)
                .maxParticipants(2)
                .teacher(firstTeacher)
                .build();
        courseRepository.save(firstCourse);

        Course secondCourse = Course.builder()
                .name("Programare paralela")
                .details("Cursul de programare paralela pentru anul II")
                .location("Amfiteatrul Haret")
                .domain("Facultatea de Matematica si Informatica")
                .duration(2)
                .participants(0)
                .maxParticipants(2)
                .teacher(secondTeacher)
                .build();
        courseRepository.save(secondCourse);

        Student firstStudent = Student.builder()
                .firstName("Amitroaiei")
                .lastName("Adrian")
                .phoneNumber("0744111111")
                .faculty("FMI")
                .build();
        studentRepository.save(firstStudent);

        Student secondStudent = Student.builder()
                .firstName("Burlacu")
                .lastName("Ioana")
                .phoneNumber("0744222222")
                .faculty("FMI")
                .build();
        studentRepository.save(secondStudent);


    }
}
