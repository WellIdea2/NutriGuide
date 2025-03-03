package com.floxie.nutri_guide.infrastructure.config.openfeign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AuthorizationInterceptorConfig {

  @Bean
  public RequestInterceptor bearerTokenInterceptor() {
    return template -> {
      // Obtain the current request attributes
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        // Get the current HttpServletRequest
        String authHeader = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          // Copy the token to the outgoing request
          template.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
      }
    };
  }
}
