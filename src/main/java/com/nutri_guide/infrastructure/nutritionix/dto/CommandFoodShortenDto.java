package com.nutri_guide.infrastructure.nutritionix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandFoodShortenDto {

  @JsonProperty("food_name")
  private String foodName;
  @JsonProperty("tag_name")
  private String tagName;

}
