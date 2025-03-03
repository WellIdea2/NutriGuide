package com.floxie.nutri_guide.features.record.entity;

import com.floxie.nutri_guide.features.meal.entity.Meal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
import org.commons.feature.user_details.enums.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"meals"})
@ToString(
    callSuper = true,
    exclude = {"meals"})
@Entity
@Table(name = "records")
public class Record extends BaseEntity {

  @Column(nullable = false)
  private UUID userId;

  @Column(nullable = false , unique = true)
  private LocalDate date = LocalDate.now();

  @Column(nullable = false , name = "daily_calories")
  private Double dailyCalories;

  @OneToOne(
      mappedBy = "record",
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
      orphanRemoval = true)
  private RecordDetails recordDetails;

  @OneToMany(mappedBy = "record", cascade = CascadeType.REMOVE)
  private List<Meal> meals = new ArrayList<>();

  public Double getCaloriesPerDay() {
    double BMR = getBmr();

    return switch (recordDetails.getWorkoutState()) {
      case SEDENTARY -> BMR * 1.2;
      case LIGHTLY_ACTIVE -> BMR * 1.375;
      case MODERATELY_ACTIVE -> BMR * 1.55;
      case VERY_ACTIVE -> BMR * 1.725;
      case SUPER_ACTIVE -> BMR * 1.9;
    };
  }

  private Double getBmr() {
    final double MALE_BMR_CONSTANT = 88.362;
    final double MALE_WEIGHT_FACTOR = 13.397;
    final double MALE_HEIGHT_FACTOR = 4.799;
    final double MALE_AGE_FACTOR = 5.677;

    final double FEMALE_BMR_CONSTANT = 447.593;
    final double FEMALE_WEIGHT_FACTOR = 9.247;
    final double FEMALE_HEIGHT_FACTOR = 3.098;
    final double FEMALE_AGE_FACTOR = 4.330;

    double weight = recordDetails.getKilograms();
    double height = recordDetails.getHeight();
    double age = recordDetails.getAge();

    double BMR;

    if (Gender.MALE == recordDetails.getGender()) {
      BMR =
          MALE_BMR_CONSTANT
              + MALE_WEIGHT_FACTOR * weight
              + MALE_HEIGHT_FACTOR * height
              - MALE_AGE_FACTOR * age;
    } else {
      BMR =
          FEMALE_BMR_CONSTANT
              + FEMALE_WEIGHT_FACTOR * weight
              + FEMALE_HEIGHT_FACTOR * height
              - FEMALE_AGE_FACTOR * age;
    }

    return BMR;
  }
}
