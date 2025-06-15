package com.example.enrollment_service.service.lesson_progress;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.enrollment_service.client.CourseClient;
import com.example.enrollment_service.client.UserClient;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;
import com.example.enrollment_service.dto.user.UserResponseDTO;
import com.example.enrollment_service.mappper.LessonProgressMapper;
import com.example.enrollment_service.model.Enrollment;
import com.example.enrollment_service.model.LessonProgress;
import com.example.enrollment_service.repository.EnrollmentRepository;
import com.example.enrollment_service.repository.LessonProgressRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonProgressServiceImpl implements LessonProgressService {

    private final LessonProgressMapper lessonProgressMapper;
    private final LessonProgressRepository lessonProgressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseClient courseClient;
    private final UserClient userClient;

    public void addLessonProgressForUser(LessonProgressRequestDTO request) {
        Boolean existUser = userClient.checkExistUser(request.getUserId());
        if (!existUser) {
            throw new EntityNotFoundException("User not found with id = " + request.getUserId());
        }
        LessonProgress lessonProgress = lessonProgressMapper.toEntity(request);
        lessonProgressRepository.save(lessonProgress);
    }

    public LessonProgressResponseDTO completedLessonForUser(LessonProgressRequestDTO request, Long userId) {
        Boolean existUser = userClient.checkExistUser(userId);
        if (!existUser) {
            throw new EntityNotFoundException("User not found with id = " + userId);
        }
        LessonProgress lessonProgress = lessonProgressMapper.toEntity(request);
        lessonProgress.setUserId(userId);
        lessonProgress.setCompleted(true);
        lessonProgress = lessonProgressRepository.save(lessonProgress);

        Optional<Enrollment> enrollmentOtp = enrollmentRepository.findByUserIdAndCourseId(userId,
                request.getCourseId());
        Enrollment enrollment = enrollmentOtp.get();
        Long totalLessonInCourse = courseClient.getTotalLessonInCourse(request.getCourseId());

        if (enrollment.getProgress() == 0.0) {
            double updateProgress = (1.0 / totalLessonInCourse) * 100;
            enrollment.setProgress(updateProgress);
            enrollmentRepository.save(enrollment);
        } else {
            Long totalLessonCompeleted = lessonProgressRepository.countByUserIdAndCourseIdAndCompletedTrue(userId,
                    request.getCourseId());
            double updateProgress = ((double) totalLessonCompeleted / totalLessonInCourse) * 100;
            enrollment.setProgress(updateProgress);
            enrollmentRepository.save(enrollment);
        }
        LessonProgressResponseDTO response = lessonProgressMapper.toDTO(lessonProgress);
        ApiResponseDTO<UserResponseDTO> user = userClient.getUserById(userId);
        response.setUser(user.getData());
        return response;
    }

}
