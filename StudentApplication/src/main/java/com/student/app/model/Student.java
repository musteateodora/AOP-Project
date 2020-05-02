package com.student.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String faculty;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "phoneNumber"),
            inverseJoinColumns = @JoinColumn(name = "courseId"))
    @JsonIgnore
    private Set<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId.equals(student.studentId) &&
                phoneNumber.equals(student.phoneNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(studentId, phoneNumber);
    }
}

