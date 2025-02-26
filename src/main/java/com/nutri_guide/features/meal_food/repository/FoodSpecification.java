package com.nutri_guide.features.meal_food.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.user.dto.UserView;
import org.commons.feature.user.enums.UserRole;
import com.nutri_guide.features.meal_food.dto.FoodFilter;
import com.nutri_guide.features.meal_food.dto.NutritionFilter;
import com.nutri_guide.features.meal_food.entity.MealFood;
import com.nutri_guide.features.shared.entity.Food;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class FoodSpecification implements Specification<MealFood> {

  private final UUID mealId;
  private final UserView user;
  private final FoodFilter foodFilter;

  @Override
  public Predicate toPredicate(
      @NonNull Root<MealFood> root, @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if(UserRole.ADMIN != user.role()) {
      predicates.add(criteriaBuilder.equal(root.get("userId"), user.id()));
    }

    if (mealId != null) {
      predicates.add(criteriaBuilder.equal(root.get("meal").get("id"), mealId));
    }

    if (foodFilter.name() != null && !foodFilter.name().isBlank()) {
      predicates.add(criteriaBuilder.like(root.get("name"), "%" + foodFilter.name() + "%"));
    }

    Set<NutritionFilter> nutrients = foodFilter.nutrients();
    if (nutrients != null && !nutrients.isEmpty()) {
      for (NutritionFilter nutrientFilter : nutrients) {
        Join<Food, ?> nutrientsJoin = root.join("nutrients", JoinType.INNER);

        if (nutrientFilter.name() != null && !nutrientFilter.name().isBlank()) {
          predicates.add(criteriaBuilder.equal(nutrientsJoin.get("name"), nutrientFilter.name()));
        }

        if (nutrientFilter.unit() != null && !nutrientFilter.unit().isBlank()) {
          predicates.add(criteriaBuilder.equal(nutrientsJoin.get("unit"), nutrientFilter.unit()));
        }

        if (nutrientFilter.biggerThen() != null) {
          predicates.add(criteriaBuilder.greaterThan(nutrientsJoin.get("amount"),
              nutrientFilter.biggerThen()));
        }

        if (nutrientFilter.smallerThen() != null) {
          predicates.add(
              criteriaBuilder.lessThan(nutrientsJoin.get("amount"), nutrientFilter.smallerThen()));
        }
      }
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}