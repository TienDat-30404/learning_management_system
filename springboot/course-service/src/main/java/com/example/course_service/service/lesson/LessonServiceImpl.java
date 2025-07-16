package com.example.course_service.service.lesson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.lesson.LessonRequestDTO;
import com.example.course_service.dto.lesson.LessonResponseDTO;
import com.example.course_service.dto.lesson.LessonUpdateDTO;
import com.example.course_service.mapper.CourseMapper;
import com.example.course_service.mapper.LessonMapper;
import com.example.course_service.repository.CourseRepository;
import com.example.course_service.repository.LessonRepository;
import com.example.course_service.service.CloudinaryService;

import jakarta.persistence.EntityNotFoundException;

import com.example.course_service.model.Course;
import com.example.course_service.model.Lesson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CloudinaryService cloudinaryService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // public Map<String, Object> getAllLessonsOfCourse(Pageable pageable, Long courseId) {
    //     Course course = courseRepository.findById(courseId)
    //         .orElseThrow(() -> new EntityNotFoundException("Course does exist with id = " + courseId));
    //     CourseResponseDTO courseData = courseMapper.toDTO(course);

    //     Page<Lesson> lessonPage = lessonRepository.findByCourseId(pageable, courseId);
    //     Page<LessonResponseDTO> lessons = lessonPage.map(lessonMapper::toDTO);
    //     CustomPageDTO listLessons = new CustomPageDTO<>(
    //             lessons.getContent(),
    //             lessons.getTotalElements(),
    //             lessons.getTotalPages());
    //     Map<String, Object> response = new HashMap();
    //     response.put("course", courseData);
    //     response.put("lessons", listLessons);
    //     return response;
        
    // }

     public CustomPageDTO<LessonResponseDTO> getAllLessonsOfCourse(Pageable pageable, Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course does exist with id = " + courseId));
        Page<Lesson> lessonPage = lessonRepository.findByCourseId(pageable, courseId);
        Page<LessonResponseDTO> lessons = lessonPage.map(lessonMapper::toDTO);
        return new CustomPageDTO<>(
                lessons.getContent(),
                lessons.getTotalElements(),
                lessons.getTotalPages());
     }

    public LessonResponseDTO createLesson(LessonRequestDTO request) {
        Lesson lesson = lessonMapper.toEntity(request);
        String videoUrl = null;
        if (request.getVideoUrl() != null && !request.getVideoUrl().isEmpty()) {
            try {
                videoUrl = cloudinaryService.uploadVideo(request.getVideoUrl());
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
            }
        }
        lesson.setVideoUrl(videoUrl);

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + request.getCourseId()));
        lesson.setCourse(course);
        lesson = lessonRepository.save(lesson);
        LessonResponseDTO response = lessonMapper.toDTO(lesson);
        return response;
    }

    public LessonResponseDTO updateLesson(Long id, LessonUpdateDTO request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id=" + id));
        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Course not found with id=" + request.getCourseId()));
            lesson.setCourse(course);
        }

        if (request.getVideoUrl() != null && !request.getVideoUrl().isEmpty()) {
            try {
                String videoUrl = cloudinaryService.uploadVideo(request.getVideoUrl());
                lesson.setVideoUrl(videoUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
            }
        }

        lessonMapper.updateLessonFromDTO(request, lesson);
        lesson = lessonRepository.save(lesson);
        LessonResponseDTO response = lessonMapper.toDTO(lesson);
        return response;
    }

    public Boolean checkLessonById(Long id) {
        return lessonRepository.existsById(id);
    }

    public LessonResponseDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id = " + id));
        return lessonMapper.toDTO(lesson);
    }

    public Long totalLessonInCourse(Long courseId) {
        return lessonRepository.countByCourse_Id(courseId);
    }

    public Long getCourseIdBasedOnLessonId(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id = " + lessonId));
        Long courseId = lesson.getCourse().getId();
        return courseId;
    }

}
