package com.floxie.nutri_guide.features.shared.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.floxie.nutri_guide.features.shared.enums.AllowedCalorieUnits;

public class ValidCalorieUnitValidator implements ConstraintValidator<ValidCalorieUnit, String> {

  @Override
  public boolean isValid(String unit, ConstraintValidatorContext context) {
    return unit != null && AllowedCalorieUnits.isValidUnit(unit);
  }
}