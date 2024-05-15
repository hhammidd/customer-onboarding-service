package com.example.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.onboarding.model")
//@EnableJpaRepositories("com.example.onboarding.repository")
public class CustomerOnboardingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOnboardingServiceApplication.class, args);
	}

}
