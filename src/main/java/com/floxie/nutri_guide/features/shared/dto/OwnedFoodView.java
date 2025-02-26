package com.floxie.nutri_guide.features.shared.dto;

import java.util.List;
import java.util.UUID;

public record OwnedFoodView (
    UUID id,
    String name,
    CalorieView calories,
    ServingView mainServing,
    FoodInfoView foodDetails,
    List<ServingView> otherServing,
    List<NutritionView> nutrients
) {

}
