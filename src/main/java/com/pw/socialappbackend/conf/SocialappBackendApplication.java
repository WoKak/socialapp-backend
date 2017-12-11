package com.pw.socialappbackend.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pw.socialappbackend.web")
public class SocialappBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialappBackendApplication.class, args);
	}
}
