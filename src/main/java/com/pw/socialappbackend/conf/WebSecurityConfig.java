package com.pw.socialappbackend.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private Filter corsFilter;

    @Autowired
    private Filter authFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(corsFilter)
                .addFilterAfter(authFilter, CustomCorsFilter.class)
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .csrf().disable();
    }
}
