package com.nutri_guide.features.shared.dto;

import java.util.List;

public record FoodView(
    String name,
    CalorieView calories,
    ServingView mainServing,
    FoodInfoView foodDetails,
    List<ServingView> otherServing,
    List<NutritionView> nutrients
) {

}
