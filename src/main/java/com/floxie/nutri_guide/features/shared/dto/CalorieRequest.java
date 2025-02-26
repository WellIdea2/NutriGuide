package com.floxie.nutri_guide.features.shared.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import com.floxie.nutri_guide.features.shared.annotations.ValidCalorieUnit;

public record CalorieRequest(

    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount cannot be negative")
    Double amount,

    @NotNull(message = "Unit is required")
    @ValidCalorieUnit
    String unit
) {

}