package com.student.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private String details;
    private String location;
    private int duration;
    private int maxParticipants;

    private String domain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return id.equals(courseDTO.id) &&
                name.equals(courseDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    private String teacherPhoneNumber;
    @JsonProperty(required = false)
    private int participants = 0;
}
