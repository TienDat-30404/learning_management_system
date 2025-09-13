package com.example.quiz_service.service.quiz_attempt;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.event.QuizAttemptEvent;
import com.example.quiz_service.client.LessonClient;
import com.example.quiz_service.client.UserClient;
import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.CustomPageDTO;
import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptRequestDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptResponseDTO;
import com.example.quiz_service.dto.user.UserResponseDTO;
import com.example.quiz_service.mapper.QuizAttemptMapper;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.QuizAttempt;
import com.example.quiz_service.repository.QuizAttemptRepository;
import com.example.quiz_service.repository.QuizRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizAttemptServiceImpl implements QuizAttemptService {
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizRepository quizRepository;
    private final QuizAttemptMapper quizAttemptMapper;
    private final UserClient userClient;
    private final LessonClient lessonClient;
    private final KafkaTemplate<String, QuizAttemptEvent> kafkaTemplate;
    @Value("${internal.api.key}")
    private String apiInternal;

    public QuizAttemptResponseDTO createQuizAttempt(QuizAttemptRequestDTO request, Long userId) {
        QuizAttempt quizAttempt = quizAttemptMapper.toEntity(request);
        Boolean existUser = userClient.checkExistUser(userId);
        if (!existUser) {
            throw new EntityNotFoundException("User not found with id = " + userId);
        } else {
            quizAttempt.setUserId(userId);
        }
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException(("Quiz not found with id = " + request.getQuizId())));
        quizAttempt.setQuiz(quiz);
        System.out.println("jgrjjrjjjjjjjjjjjjjjjjjjjjj"
                + quizAttemptRepository.existsByUserIdAndQuizId(userId, request.getQuizId()));
        if (!quizAttemptRepository.existsByUserIdAndQuizId(userId, request.getQuizId())) {
            System.out.println("123322222222222222222222222kgjgjrgjrfgrfg");
            Long courseId = lessonClient.findCourseIdByLessonId(quiz.getLessonId());
            // if (request.getScore().compareTo(BigDecimal.valueOf(8)) >= 0) {
            QuizAttemptEvent quizAttemptEvent = new QuizAttemptEvent(
                    userId,
                    quiz.getLessonId(),
                    courseId);
            kafkaTemplate.send("quiz-attempt-event", quizAttemptEvent);
            // }
        }
        quizAttempt = quizAttemptRepository.save(quizAttempt);
        QuizAttemptResponseDTO response = quizAttemptMapper.toDTO(quizAttempt);

        ApiResponseDTO<UserResponseDTO> user = userClient.getUserById(userId);

        response.setUser(user.getData());
        return response;
    }

    public CustomPageDTO<QuizAttemptResponseDTO> getResultQuizByUser(Pageable pageable, Long userId) {
        Page<QuizAttempt> pageQuizAttempt = quizAttemptRepository.findByUserId(pageable, userId);
        Page<QuizAttemptResponseDTO> quizAttempts = pageQuizAttempt.map(quizAttemptMapper::toDTO);

        return new CustomPageDTO<>(
                quizAttempts.getContent(), quizAttempts.getTotalElements(), quizAttempts.getTotalPages());
    }

    public List<QuizAttemptResponseDTO> getHistoryQuizOfUser(Long userId, Long quizId) {
        List<QuizAttempt> historyQuiz = quizAttemptRepository.findAllByUserIdAndQuizId(userId, quizId);
         return historyQuiz.stream()
                .map(quizAttemptMapper::toDTO) 
                .collect(Collectors.toList());
    }

}
