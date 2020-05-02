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
public class Course {
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "courseId")},
            inverseJoinColumns = @JoinColumn(name = "phoneNumber"))
    Set<Student> studentSet;

    private String name;
    private String details;
    private String location;
    private int duration;
    private int maxParticipants;
    private String domain;
    private int participants;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber")
    @JsonIgnore
    private Teacher teacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) &&
                id.equals(course.id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
