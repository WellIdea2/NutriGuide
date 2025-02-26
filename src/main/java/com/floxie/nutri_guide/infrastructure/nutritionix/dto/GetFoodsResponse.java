package com.floxie.nutri_guide.infrastructure.nutritionix.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFoodsResponse {

  private List<FoodItem> foods;
}
