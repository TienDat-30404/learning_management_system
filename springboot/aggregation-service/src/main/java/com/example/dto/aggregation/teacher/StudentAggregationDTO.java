package com.example.dto.aggregation.teacher;

import com.example.dto.CourseDTO;
import com.example.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentAggregationDTO {
    private Long id;
    private double progress;
    private UserDTO user;
    private CourseDTO course;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;
}
