package com.example.user_service;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class UserServiceApplication {

	@GetMapping("/")
	public String sayHello() {
		return "Helloee UserSe2rvice";
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
