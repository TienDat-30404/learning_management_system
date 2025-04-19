package com.example.course_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableCaching
public class CourseServiceApplication {

	@GetMapping("/")
	public String sayHello() {
		return "Helloee CourseService!";
	}

	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}

}
