package com.nutri_guide.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.commons.feature.nutri_guide.paths.NutritionixApiControllerPaths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] WHITE_LIST = {
      NutritionixApiControllerPaths.BASE + "/**",
  };
  private final UserHeaderFilter userHeaderFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated())
        .addFilterBefore(userHeaderFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}