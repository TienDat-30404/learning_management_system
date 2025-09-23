package com.example.quiz_service.service.quiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quiz_service.client.LessonClient;
import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.CustomPageDTO;
import com.example.quiz_service.dto.lesson.LessonResponseDTO;
import com.example.quiz_service.dto.quiz.QuizRequestDTO;
import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.mapper.QuizMapper;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.QuizAnswer;
import com.example.quiz_service.model.QuizQuestion;
import com.example.quiz_service.repository.QuizAnswerRepository;
import com.example.quiz_service.repository.QuizQuestionRepository;
import com.example.quiz_service.repository.QuizRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizMapper quizMapper;
    private final LessonClient lessonClient;

    public QuizResponseDTO createQuiz(QuizRequestDTO request) {
        Long lessonId = request.getLessonId();
        Boolean existLesson = lessonClient.checkLessonById(lessonId);
        if (!existLesson) {
            throw new EntityNotFoundException("Lesson not found with id = " + lessonId);
        }
        Quiz quiz = quizMapper.toEntity(request);
        quiz = quizRepository.save(quiz);

        for (QuizRequestDTO.QuizQuestionDTO quizQuestionDTO : request.getQuestions()) {
            QuizQuestion question = new QuizQuestion();
            question.setQuiz(quiz);
            question.setQuestion(quizQuestionDTO.getQuestion());
            question = quizQuestionRepository.save(question);

            for (QuizRequestDTO.QuizAnswerDTO quizAnswerDTO : quizQuestionDTO.getAnswers()) {
                QuizAnswer answer = new QuizAnswer();
                answer.setAnswer(quizAnswerDTO.getAnswer());
                answer.setCorrect(quizAnswerDTO.isCorrect());
                answer.setQuizQuestion(question);
                quizAnswerRepository.save(answer);
            }
        }

        QuizResponseDTO response = quizMapper.toDTO(quiz);
        List<QuizResponseDTO.QuestionDTO> questionDTOList = new ArrayList<>();
        List<QuizQuestion> savedQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
        for (QuizQuestion question : savedQuestions) {
            QuizResponseDTO.QuestionDTO questionDTO = new QuizResponseDTO.QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());

            List<QuizResponseDTO.AnswerDTO> answerDTOList = new ArrayList<>();
            List<QuizAnswer> answers = quizAnswerRepository.findByQuizQuestionId(question.getId());

            for (QuizAnswer answer : answers) {
                QuizResponseDTO.AnswerDTO answerDTO = new QuizResponseDTO.AnswerDTO();
                answerDTO.setId(answer.getId());
                answerDTO.setAnswer(answer.getAnswer());
                answerDTO.setCorrect(answer.isCorrect());
                answerDTOList.add(answerDTO);
            }

            questionDTO.setAnswers(answerDTOList);
            questionDTOList.add(questionDTO);
        }

        response.setQuestions(questionDTOList);
        ApiResponseDTO<LessonResponseDTO> lesson = lessonClient.getLessonById(lessonId);
        response.setLesson(lesson.getData());
        return response;
    }

    public QuizResponseDTO getQuizForLesson(Long lessonId) {
        Boolean existLesson = lessonClient.checkLessonById(lessonId);
        if (!existLesson) {
            throw new EntityNotFoundException("Not found quiz for lesson = " + lessonId);
        }

        Quiz quiz = quizRepository.findByLessonId(lessonId);
        QuizResponseDTO response = quizMapper.toDTO(quiz);
        List<QuizResponseDTO.QuestionDTO> questionDTOList = new ArrayList<>();
        List<QuizQuestion> savedQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
        for (QuizQuestion question : savedQuestions) {
            QuizResponseDTO.QuestionDTO questionDTO = new QuizResponseDTO.QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());

            List<QuizResponseDTO.AnswerDTO> answerDTOList = new ArrayList<>();
            List<QuizAnswer> answers = quizAnswerRepository.findByQuizQuestionId(question.getId());

            for (QuizAnswer answer : answers) {
                QuizResponseDTO.AnswerDTO answerDTO = new QuizResponseDTO.AnswerDTO();
                answerDTO.setId(answer.getId());
                answerDTO.setAnswer(answer.getAnswer());
                answerDTO.setCorrect(answer.isCorrect());
                answerDTOList.add(answerDTO);
            }

            questionDTO.setAnswers(answerDTOList);
            questionDTOList.add(questionDTO);
        }

        response.setQuestions(questionDTOList);
        ApiResponseDTO<LessonResponseDTO> lesson = lessonClient.getLessonById(lessonId);
        response.setLesson(lesson.getData());
        return response;
    }

    public boolean checkExistLesson(Long lessonId) {
        return quizRepository.existsByLessonId(lessonId);
    }

    public long countNumberOfQuizs(List<Long> lessonIds) {
        long number = quizRepository.countByLessonIdIn(lessonIds);
        return number;
    }

}
