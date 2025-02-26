package com.floxie.nutri_guide.features.meal_food.repository;

import java.util.Optional;
import java.util.UUID;
import com.floxie.nutri_guide.features.meal_food.entity.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, UUID>,
    JpaSpecificationExecutor<MealFood> {

  @Query("SELECT f FROM MealFood f WHERE f.id = :foodId "
      + "AND f.meal.id = :mealId")
  Optional<MealFood> findByIdAndMealId(@Param("foodId") UUID foodId,
      @Param("mealId") UUID mealId);

  @Query("SELECT CASE WHEN COUNT(f) > 0 "
      + "THEN TRUE ELSE FALSE END "
      + "FROM MealFood f WHERE f.id = :foodId "
      + "AND f.meal.id = :mealId "
      + "AND f.userId = :userId")
  boolean existsByIdAndMealIdAndUserId(@Param("foodId") UUID foodId,
      @Param("mealId") UUID mealId,
      @Param("userId") UUID userId);

  boolean existsByIdAndMealId(UUID foodId, UUID mealId);
}