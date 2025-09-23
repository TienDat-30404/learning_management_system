package com.example.dto.aggregation;

import lombok.Data;

@Data
public class LessonProgressAggregationDTO {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private Boolean isCompleted;
}
