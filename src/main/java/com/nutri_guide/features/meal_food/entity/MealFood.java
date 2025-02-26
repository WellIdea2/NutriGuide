package com.nutri_guide.features.meal_food.entity;

import com.nutri_guide.features.meal.entity.Meal;
import com.nutri_guide.features.shared.entity.Food;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("MEAL_FOOD")
public class MealFood extends Food {

  @ManyToOne
  private Meal meal;
}
