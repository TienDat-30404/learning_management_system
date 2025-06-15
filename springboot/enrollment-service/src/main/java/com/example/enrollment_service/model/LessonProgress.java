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
@Table(name = "lesson_progress")
@Data
@EqualsAndHashCode(callSuper = true)

public class LessonProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User can not be null")
    private Long userId;

    private Long lessonId;

    @NotNull(message = "Course can not be null")
    private Long courseId;

    @NotNull(message = "Completed can not be null")
    private Boolean completed;
}
