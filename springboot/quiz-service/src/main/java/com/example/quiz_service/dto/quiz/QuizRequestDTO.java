package com.example.quiz_service.dto.quiz;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizRequestDTO {
    @NotBlank(message = "Title can not be blank")
    private String title;

    @NotBlank(message = "Description can not be blank")
    private String description;

    @NotNull(message = "Lesson can not be null")
    private Long lessonId;

    private List<QuizQuestionDTO> questions;

    @Data
    public static class QuizQuestionDTO {
        private String question;
        private List<QuizAnswerDTO> answers;
    }

    @Data
    public static class QuizAnswerDTO {
        private String answer;
        private boolean correct;
    }
}
