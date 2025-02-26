package com.nutri_guide.infrastructure.config.security;

import org.commons.feature.user.dto.UserView;
import org.commons.exceptions.throwable.ForbiddenException;
import com.nutri_guide.infrastructure.exception.ExceptionMessages;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private SecurityUtils() {
  }

  public static UserView getCurrentLoggedInUser() {
    if (SecurityContextHolder.getContext()
        .getAuthentication() instanceof CustomAuthenticationToken authentication) {
      return authentication.getPrincipal();
    }
    throw new ForbiddenException(ExceptionMessages.INVALID_USER_TOKEN);
  }
}