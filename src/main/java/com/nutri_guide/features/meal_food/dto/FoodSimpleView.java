package com.nutri_guide.features.meal_food.dto;

import com.nutri_guide.features.shared.dto.CalorieView;
import com.nutri_guide.features.shared.dto.ServingView;

public record FoodSimpleView(
    String name,
    CalorieView calories,
    ServingView serving
) {

}
