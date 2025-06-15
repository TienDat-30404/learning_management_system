package com.example.quiz_service.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "quiz_attempts")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)

public class QuizAttempt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User can not be null")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Quiz can not be null")
    private Quiz quiz;

    @NotNull(message = "Score must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Score must be at least 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Score must not exceed 10")
    private BigDecimal score;
}
