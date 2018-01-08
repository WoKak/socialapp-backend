package com.pw.socialappbackend.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private Filter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(corsFilter)
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .csrf().disable();
    }
}
