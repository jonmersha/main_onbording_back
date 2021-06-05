package com.coopay;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.coopay.imageUpload.storage.StorageProperties;
import com.coopay.imageUpload.storage.StorageService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class OnboardingApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnboardingApplication.class, args);
	}

	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
	
	
	
}
