package com.floxie.nutri_guide.features.shared.dto;

import org.hibernate.validator.constraints.Length;

public record FoodInfoRequest(

    @Length(max = 255, message = "Info must be at most 255 characters")
    String info,

    String largeInfo,

    @Length(max = 5000, message = "Picture must be at most 1500 characters")
    String picture
) {

}