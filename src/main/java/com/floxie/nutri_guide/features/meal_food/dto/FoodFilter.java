package com.floxie.nutri_guide.features.meal_food.dto;

import jakarta.validation.Valid;
import java.util.Set;

public record FoodFilter(
    String name,

    @Valid
    Set<NutritionFilter> nutrients
) {

}
