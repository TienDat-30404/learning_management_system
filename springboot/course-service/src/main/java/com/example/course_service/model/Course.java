package com.example.course_service.model;

import java.math.BigDecimal;

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
@Table(name = "courses")
@Data
@EqualsAndHashCode(callSuper=true)
public class Course extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Description is required")    
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Image is required")
    private String image;

    @Column(nullable = false)
    @NotBlank(message = "Category is required")
    private Long categoryId;


    @Column(nullable = false)
    @NotBlank(message = "User is required")
    private Long userId;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;
}
