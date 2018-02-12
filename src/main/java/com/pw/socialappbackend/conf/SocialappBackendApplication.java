package com.pw.socialappbackend.conf;

import com.pw.socialappbackend.service.TweetService;
import com.pw.socialappbackend.service.impl.TweetServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@SpringBootApplication(scanBasePackages = "com.pw.socialappbackend.web")
public class SocialappBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialappBackendApplication.class, args);
	}

	@Bean
	public Filter corsFilter() {
		return new CustomCorsFilter();
	}

	@Bean
	public Filter authFilter() {
		return new AuthenticationFilter();
	}


	@Bean
	public TweetService tweetService() {
		return new TweetServiceImpl();
	}
}
