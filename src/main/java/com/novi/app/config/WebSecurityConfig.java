package com.novi.app.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.novi.app")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebSecurityConfig {
    // TODO: add dependency
}
