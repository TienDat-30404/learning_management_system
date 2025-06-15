package com.example.quiz_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "quizs")
@Data
@EqualsAndHashCode(callSuper=true)
public class Quiz extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")    
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Desciption is required")    
    private String description;


}
