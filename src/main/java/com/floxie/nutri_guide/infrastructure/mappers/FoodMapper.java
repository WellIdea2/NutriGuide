package com.floxie.nutri_guide.infrastructure.mappers;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.floxie.nutri_guide.features.custom_food.entity.CustomFood;
import com.floxie.nutri_guide.features.meal_food.dto.FoodSimpleView;
import com.floxie.nutri_guide.features.meal_food.entity.MealFood;
import com.floxie.nutri_guide.features.shared.dto.CalorieView;
import com.floxie.nutri_guide.features.shared.dto.FoodInfoView;
import com.floxie.nutri_guide.features.shared.dto.FoodRequest;
import com.floxie.nutri_guide.features.shared.dto.FoodView;
import com.floxie.nutri_guide.features.shared.dto.NutritionView;
import com.floxie.nutri_guide.features.shared.dto.OwnedFoodView;
import com.floxie.nutri_guide.features.shared.dto.ServingView;
import com.floxie.nutri_guide.features.shared.entity.Food;
import com.floxie.nutri_guide.features.shared.enums.AllowedCalorieUnits;
import com.floxie.nutri_guide.infrastructure.nutritionix.dto.FoodItem;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(FoodMapperDecoder.class)
public interface FoodMapper {

  @Mapping(target = "mainServing", ignore = true)
  @Mapping(target = "otherServing", ignore = true)
  OwnedFoodView toView(Food entity);

  @Mapping(target = "mainServing", ignore = true)
  @Mapping(target = "otherServing", ignore = true)
  OwnedFoodView toView(CustomFood entity);

  FoodSimpleView toSimpleView(Food entity);

  @Mapping(target = "servingPortions", ignore = true)
  CustomFood toEntityCustom(FoodRequest dto);

  @Mapping(target = "servingPortions", ignore = true)
  MealFood toEntityMeal(FoodRequest dto);

  default FoodView toView(FoodItem item) {
    CalorieView calorieView = new CalorieView(
        item.getNfCalories(), AllowedCalorieUnits.CALORIE.getSymbol()
    );
    FoodInfoView foodInfoView = FoodInfoMapper.generateFoodInfo(item);
    List<NutritionView> nutrients = NutrientMapper.getNutrients(item);
    List<ServingView> servings = ServingMapper.getServings(item);
    ServingView mainServing = ServingMapper.getMainServing(item);

    return new FoodView(
        item.getFoodName(),
        calorieView,
        mainServing,
        foodInfoView,
        servings,
        nutrients
    );
  }
}
