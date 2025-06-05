package com.example.review_service.dto.review;

import com.example.review_service.enums.TargetType;

import lombok.Data;

@Data
public class ReviewUpdateDTO {
    
    private String comment;

    private Long rating;

}
