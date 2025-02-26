package com.nutri_guide.features.meal_food.service;

import static com.nutri_guide.infrastructure.exception.ExceptionMessages.FOOD_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.exceptions.throwable.NotFoundException;
import com.nutri_guide.features.meal.services.MealService;
import com.nutri_guide.features.meal_food.dto.FoodFilter;
import com.nutri_guide.features.meal_food.entity.MealFood;
import com.nutri_guide.features.meal_food.repository.FoodSpecification;
import com.nutri_guide.features.meal_food.repository.MealFoodRepository;
import com.nutri_guide.features.shared.dto.FoodRequest;
import com.nutri_guide.features.shared.dto.OwnedFoodView;
import com.nutri_guide.infrastructure.config.security.SecurityUtils;
import com.nutri_guide.infrastructure.mappers.FoodMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MealFoodServiceImpl implements MealFoodService {

  private final MealService mealService;
  private final FoodMapper foodMapper;
  private final MealFoodRepository repository;

  public Page<OwnedFoodView> getAll(UUID mealId, FoodFilter filter, Pageable pageable) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    FoodSpecification specification = new FoodSpecification(mealId, user, filter);

    return repository.findAll(specification, pageable)
        .map(foodMapper::toView);
  }

  public OwnedFoodView get(UUID foodId, UUID mealId) {
    return foodMapper.toView(findByIdMealId(foodId, mealId));
  }

  public OwnedFoodView create(UUID mealId, FoodRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();
    var meal = mealService.findByIdAndUserId(mealId, user.id());

    var entity = foodMapper.toEntityMeal(dto);

    entity.setMeal(meal);
    entity.setUserId(user.id());

    return foodMapper.toView(repository.save(entity));
  }

  @Transactional
  public OwnedFoodView update(UUID mealId, UUID foodId, FoodRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();
    var food = findByIdMealId(foodId, mealId);

    var entity = foodMapper.toEntityMeal(dto);
    entity.setMeal(food.getMeal());
    entity.setUserId(user.id());

    repository.delete(food);
    return foodMapper.toView(repository.save(entity));
  }

  public void delete(UUID mealId, UUID foodId) {
    if (!repository.existsByIdAndMealId(foodId, mealId)) {
      throw new NotFoundException(FOOD_NOT_FOUND, foodId);
    }

    repository.deleteById(foodId);
  }

  public MealFood findByIdMealId(UUID foodId, UUID mealId) {
    return repository.findByIdAndMealId(foodId, mealId)
        .orElseThrow(() -> new NotFoundException(FOOD_NOT_FOUND, foodId));
  }

  public boolean isOwner(UUID mealId, UUID foodId, UUID userId) {
    return repository.existsByIdAndMealIdAndUserId(foodId, mealId, userId);
  }
}