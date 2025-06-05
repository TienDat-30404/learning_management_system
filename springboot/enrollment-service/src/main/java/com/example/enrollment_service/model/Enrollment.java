package com.example.enrollment_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "enrollments")
@Data
@EqualsAndHashCode(callSuper=true)

public class Enrollment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @NotNull(message = "UserId can not be null")
    private Long userId;

    @NotNull(message = "CourseId can not be null")
    private Long courseId;

    @NotNull(message = "Progress can not be null")
    private Double progress;
}
