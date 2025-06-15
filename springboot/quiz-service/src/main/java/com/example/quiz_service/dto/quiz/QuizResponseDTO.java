package com.example.quiz_service.dto.quiz;

import java.util.List;

import com.example.quiz_service.dto.lesson.LessonResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizResponseDTO {
    private Long id;
    private LessonResponseDTO lesson;
    private String title;
    private String description;
    private List<QuestionDTO> questions; 

    @Data
    public static class QuestionDTO {
        private Long id;
        private String question;
        private List<AnswerDTO> answers;
    }

    @Data
    public static class AnswerDTO {
        private Long id;
        private String answer;
        private boolean isCorrect;
    }
}
