package com.example.quiz_service.dto.quiz_attempt;

import java.math.BigDecimal;

import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.dto.user.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class QuizAttemptResponseDTO {
    private Long id;
    private QuizResponseDTO quiz;
    private UserResponseDTO user;
    private BigDecimal score;
}
