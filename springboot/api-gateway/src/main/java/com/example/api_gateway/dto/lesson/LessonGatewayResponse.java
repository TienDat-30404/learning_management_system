package com.example.api_gateway.dto.lesson;

import lombok.Data;

@Data
public class LessonGatewayResponse {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
}
