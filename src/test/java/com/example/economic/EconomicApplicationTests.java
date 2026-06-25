package com.example.economic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class EconomicApplicationTests {

	@Test
	void contextLoads() {
	}
	@Bean
	public RestTemplate defaultRestTemplate(){
		return new RestTemplate();
	}
}
