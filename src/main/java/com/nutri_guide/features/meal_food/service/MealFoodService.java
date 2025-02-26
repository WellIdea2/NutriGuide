package com.nutri_guide.features.meal_food.service;

import java.util.UUID;
import com.nutri_guide.features.shared.dto.FoodRequest;
import com.nutri_guide.features.shared.dto.OwnedFoodView;
import com.nutri_guide.features.meal_food.dto.FoodFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MealFoodService {

  OwnedFoodView create(UUID mealId, FoodRequest dto);

  void delete(UUID mealId, UUID foodId);

  OwnedFoodView get(UUID foodId, UUID mealId);

  OwnedFoodView update(UUID mealId, UUID foodId, FoodRequest dto);

  Page<OwnedFoodView> getAll(UUID mealId, FoodFilter filter, Pageable pageable);

  boolean isOwner(UUID mealId , UUID foodId, UUID userId);
}