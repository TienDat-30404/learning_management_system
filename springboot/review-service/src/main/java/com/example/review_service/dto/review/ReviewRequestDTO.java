package com.example.review_service.dto.review;

import com.example.review_service.enums.TargetType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDTO {

    @NotBlank(message = "Mô tả không được để trống")
    private String comment;

    @NotNull(message = "Rating không được để trống")
    @Min(value = 1, message = "Rating phải lớn hơn hoặc bằng 1")
    @Max(value = 5, message = "Rating không được lớn hơn 5")
    private Long rating;


    @NotNull(message = "TargetType không được để trống")
    private TargetType targetType;

    @NotNull(message = "TargetId không được để trống")
    private Long targetId;
}
