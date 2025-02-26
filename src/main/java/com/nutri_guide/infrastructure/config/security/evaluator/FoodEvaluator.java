package com.nutri_guide.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.nutri_guide.features.meal_food.service.MealFoodService;
import com.nutri_guide.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodEvaluator {

  private final MealFoodService foodService;

  public boolean isOwner(UUID mealId , UUID foodId) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return foodService.isOwner(mealId , foodId, user.id());
  }
}
