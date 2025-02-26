package com.floxie.nutri_guide.features.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import com.floxie.nutri_guide.features.shared.annotations.ValidNutrition;

@ValidNutrition
public record NutritionRequest(

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Unit is required")
    String unit,

    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount cannot be negative")
    Double amount
) {

}
