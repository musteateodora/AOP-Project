package com.student.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private String details;
    private String location;
    private long dateTime;
    private int duration;
    private int maxParticipants;
    private String domain;
    private String teacherPhoneNumber;
    @JsonProperty(required = false)
    private int participants = 0;
}
