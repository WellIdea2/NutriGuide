package com.nutri_guide.features.meal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.commons.feature.shared.entity.BaseEntity;
import com.nutri_guide.features.meal_food.entity.MealFood;
import com.nutri_guide.features.record.entity.Record;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"record", "foods"})
@ToString(callSuper = true, exclude = {"record", "foods"})
@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

  @Column(nullable = false)
  private String name = "Default Meal";

  @ManyToOne
  private Record record;

  @OneToMany(
      mappedBy = "meal",
      cascade = CascadeType.REMOVE,
      fetch = FetchType.EAGER
  )
  private Set<MealFood> foods = new HashSet<>();
}