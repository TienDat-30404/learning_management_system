package com.example.review_service.model;

import com.example.review_service.enums.TargetType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "reviews")
@Data
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User Id cannot be null")
    private Long userId;

    @NotNull(message = "TargetType không được để trống")
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @NotNull(message = "TargetId không được để trống")
    private Long targetId;

    @NotNull(message = "Rating không được để trống")
    @Min(value = 1, message = "Rating phải lớn hơn hoặc bằng 1")
    @Max(value = 5, message = "Rating không được lớn hơn 5")
    private Long rating;
    
    @NotBlank(message = "Mô tả không được để trống")
    private String comment;
}