package com.student.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    Set<Student> studentSet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String details;
    private String location;
    private long dateTime;
    private int duration;
    private int maxParticipants;
    private String domain;
    private int participants;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber")
    private Teacher teacher;
}
