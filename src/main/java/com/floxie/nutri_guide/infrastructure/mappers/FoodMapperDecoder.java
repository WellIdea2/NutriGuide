package com.floxie.nutri_guide.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.floxie.nutri_guide.features.custom_food.entity.CustomFood;
import com.floxie.nutri_guide.features.meal_food.dto.FoodSimpleView;
import com.floxie.nutri_guide.features.meal_food.entity.MealFood;
import com.floxie.nutri_guide.features.shared.dto.FoodRequest;
import com.floxie.nutri_guide.features.shared.dto.OwnedFoodView;
import com.floxie.nutri_guide.features.shared.entity.Food;
import com.floxie.nutri_guide.features.shared.entity.Serving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class FoodMapperDecoder implements FoodMapper {

  private FoodMapper delegate;

  @Override
  public OwnedFoodView toView(Food entity) {
    var foodView = delegate.toView(entity);

    var mainServingView = entity.getServingPortions()
        .stream()
        .filter(Serving::isMain)
        .findFirst()
        .map(ServingMapper::toView)
        .orElseThrow();

    var otherServingView = entity.getServingPortions()
        .stream()
        .filter(servingPortion -> !servingPortion.isMain())
        .map(ServingMapper::toView)
        .toList();

    return new OwnedFoodView(
        entity.getId(),
        foodView.name(),
        foodView.calories(),
        mainServingView,
        foodView.foodDetails(),
        otherServingView,
        foodView.nutrients()
    );
  }

  @Override
  public FoodSimpleView toSimpleView(Food entity) {
    var foodSimpleView = delegate.toSimpleView(entity);

    var mainServingView = entity.getServingPortions()
        .stream()
        .filter(Serving::isMain)
        .findFirst()
        .map(ServingMapper::toView)
        .orElseThrow();

    return new FoodSimpleView(
        foodSimpleView.name(),
        foodSimpleView.calories(),
        mainServingView
    );
  }

  @Override
  public CustomFood toEntityCustom(FoodRequest dto) {
    var entity = delegate.toEntityCustom(dto);

    mapCommonAttributesFromRequest(dto, entity);

    return entity;
  }

  @Override
  public MealFood toEntityMeal(FoodRequest dto) {
    var entity = delegate.toEntityMeal(dto);

    mapCommonAttributesFromRequest(dto, entity);

    return entity;
  }

  private void mapCommonAttributesFromRequest(FoodRequest dto, Food entity) {
    List<Serving> servingPortions = new ArrayList<>();

    entity.getNutrients()
        .forEach(nutrient -> nutrient.setFood(entity));

    var mainServing = ServingMapper.toEntity(dto.mainServing(), entity);
    mainServing.setMain(true);
    servingPortions.add(mainServing);

    Optional.ofNullable(dto.otherServing())
        .ifPresent(servingRequests -> servingRequests
            .forEach(servingRequest -> servingPortions.add(
                ServingMapper.toEntity(servingRequest, entity))));

    entity.setServingPortions(servingPortions);
  }

  @Autowired
  public void setDelegate(@Qualifier("delegate") FoodMapper delegate) {
    this.delegate = delegate;
  }
}
