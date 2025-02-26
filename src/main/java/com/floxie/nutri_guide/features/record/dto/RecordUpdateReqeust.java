package com.floxie.nutri_guide.features.record.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record RecordUpdateReqeust(

    @PositiveOrZero(message = "Daily calories cannot be negative")
    Double dailyCalories
) {

}
