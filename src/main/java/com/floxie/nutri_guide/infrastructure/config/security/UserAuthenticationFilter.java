package com.floxie.nutri_guide.infrastructure.config.security;

import com.floxie.nutri_guide.infrastructure.config.openfeign.UserClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.commons.feature.user.dto.UserView;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

  private final UserClient userClient;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      try {
        UserView user = userClient.getCurrentUser();

        if (user != null) {
          List<SimpleGrantedAuthority> authorities =
              List.of(new SimpleGrantedAuthority("ROLE_" + user.role()));

          CustomAuthenticationToken authentication =
              new CustomAuthenticationToken(user, authorities);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
