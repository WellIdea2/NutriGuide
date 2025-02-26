package com.nutri_guide.infrastructure.mappers;

import java.util.Optional;
import com.nutri_guide.features.shared.dto.FoodInfoView;
import com.nutri_guide.infrastructure.nutritionix.dto.FoodItem;
import com.nutri_guide.infrastructure.nutritionix.dto.Photo;
import com.nutri_guide.infrastructure.nutritionix.dto.Tags;

public class FoodInfoMapper {

  public static FoodInfoView generateFoodInfo(FoodItem foodItem) {
    return Optional.ofNullable(foodItem.getBrandName())
        .map(brandName ->
            new FoodInfoView(brandName,
                foodItem.getNfIngredientStatement(),
                getPicture(foodItem))
        )
        .orElse(
            new FoodInfoView(Optional.ofNullable(foodItem.getTags())
                .map(Tags::getItem)
                .orElse(null),
                foodItem.getNfIngredientStatement(),
                getPicture(foodItem)));
  }

  private static String getPicture(FoodItem foodItem) {
    return Optional.ofNullable(foodItem.getPhoto())
        .map(Photo::getThumb)
        .orElse(null);
  }
}
