package com.floxie.nutri_guide.features.shared.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.commons.feature.shared.entity.BaseEntity;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "foods")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "food_type")
public class Food extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private UUID userId;

  @Embedded
  private Calories calories;

  @Embedded
  private FoodInfo foodDetails;

  @OneToMany(
      mappedBy = "food",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      fetch = FetchType.EAGER
  )
  private List<Serving> servingPortions = new ArrayList<>();

  @OneToMany(
      mappedBy = "food",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      fetch = FetchType.EAGER
  )
  private List<Nutrient> nutrients = new ArrayList<>();
}