package com.nutri_guide.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.user.dto.UserView;
import com.nutri_guide.features.custom_food.service.CustomFoodService;
import com.nutri_guide.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomFoodEvaluator {

  private final CustomFoodService customFoodService;

  public boolean isOwner(UUID foodId) {
    UserView user = SecurityUtils.getCurrentLoggedInUser();

    return customFoodService.existsByUserIdAndFoodId(user.id(), foodId);
  }
}
