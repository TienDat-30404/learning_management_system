package com.example.quiz_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.quiz.QuizRequestDTO;
import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.service.quiz.QuizService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/quizs")
@Controller
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<QuizResponseDTO>> createQuiz(
            @Valid @RequestBody QuizRequestDTO request) {
        QuizResponseDTO quiz = quizService.createQuiz(request);
        ApiResponseDTO<QuizResponseDTO> resposne = new ApiResponseDTO<>(
                201, quiz, "Create quiz successfully");
        return ResponseEntity.ok(resposne);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<QuizResponseDTO>> getQuizByLesson(
            @RequestParam(value = "lessonId") Long lessonId) {
        QuizResponseDTO quiz = quizService.getQuizForLesson(lessonId);
        ApiResponseDTO<QuizResponseDTO> response = new ApiResponseDTO<>(
                200, quiz, "Quiz with lesson : " + lessonId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-lesson/{lessonId}")
    public ResponseEntity<Boolean> checkExistLesson(@PathVariable Long lessonId) {
        Boolean response = quizService.checkExistLesson(lessonId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-quiz-of-courses")
    public ResponseEntity<ApiResponseDTO<Long>> getCountQuizForCourses(
            @RequestParam("lessonId") List<Long> lessonIds) {

        long count = quizService.countNumberOfQuizs(lessonIds);
        ApiResponseDTO<Long> response = new ApiResponseDTO<>(
                200, count, "Total quiz of courses" + count);
        return ResponseEntity.ok(response);
    }
}
