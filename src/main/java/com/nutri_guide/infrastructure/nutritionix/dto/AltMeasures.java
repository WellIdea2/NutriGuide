package com.nutri_guide.infrastructure.nutritionix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AltMeasures {

  @JsonProperty("serving_weight")
  private Double servingWeight;
  private String measure;
  private Double seq;
  private Double qty;
}
