package com.example.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer; // Import annotation
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigServer 

public class ConfigServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
