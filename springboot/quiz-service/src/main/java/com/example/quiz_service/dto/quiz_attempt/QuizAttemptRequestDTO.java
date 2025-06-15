package com.example.quiz_service.dto.quiz_attempt;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAttemptRequestDTO {
    @NotNull(message = "Quiz can not be null")
    private Long quizId;


    @NotNull(message = "Score must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Score must be at least 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Score must not exceed 10")
    
    private BigDecimal score;

}
