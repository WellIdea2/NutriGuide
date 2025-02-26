package com.nutri_guide.features.meal_food.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record NutritionFilter(
    String name,

    String unit,

    @PositiveOrZero(message = "Amount cannot be negative")
    Double biggerThen,

    @PositiveOrZero(message = "Amount cannot be negative")
    Double smallerThen
) {

}
