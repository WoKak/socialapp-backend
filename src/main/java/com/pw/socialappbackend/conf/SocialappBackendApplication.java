package com.pw.socialappbackend.conf;

import com.pw.socialappbackend.service.AuthenticationService;
import com.pw.socialappbackend.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.pw.socialappbackend.service.TweetService;
import com.pw.socialappbackend.service.impl.TweetServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages = "com.pw.socialappbackend.web")
@PropertySource(value = {"classpath:db.properties"})
public class SocialappBackendApplication {

	@Autowired
	Environment env;

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

	@Bean
	public AuthenticationService authenticationService() {
		return new AuthenticationServiceImpl();
	}

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.drivers"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));

		return dataSource;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(15);
	}
}
