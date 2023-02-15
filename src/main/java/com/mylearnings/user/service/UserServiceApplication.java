package com.mylearnings.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

	// either you can declare  bean config here  or make a dedicated config class to load bean.
//	@Bean
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//
//	}
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
