package com.example.economic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EconomicApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconomicApplication.class, args);
	}

	@Bean
	public RestTemplate defaultRestTemplate(){
		return new RestTemplate();
	}
}
