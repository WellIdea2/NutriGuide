package com.nutri_guide.infrastructure.config;

import org.commons.exceptions.CustomExceptionHandler;
import org.commons.exceptions.FeignExceptionHandler;
import org.commons.exceptions.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

  @Bean
  public CustomExceptionHandler customExceptionHandler() {
    return new CustomExceptionHandler();
  }
  
  @Bean
  public GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }

  @Bean
  public FeignExceptionHandler feignExceptionHandler() {
    return new FeignExceptionHandler();
  }
}
