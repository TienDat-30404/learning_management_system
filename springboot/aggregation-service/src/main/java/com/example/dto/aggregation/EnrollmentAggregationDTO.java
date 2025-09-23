package com.example.dto.aggregation;


import com.example.dto.CourseDTO;
import com.example.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EnrollmentAggregationDTO {
    private Long id;
    private Double progress;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    private UserDTO user;
    private CourseDTO course;
}
