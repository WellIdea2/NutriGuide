package com.floxie.nutri_guide.features.shared.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.floxie.nutri_guide.features.shared.enums.AllowedNutrients;
import com.floxie.nutri_guide.features.shared.dto.NutritionRequest;

public class ValidNutritionValidator implements
    ConstraintValidator<ValidNutrition, NutritionRequest> {

  @Override
  public boolean isValid(NutritionRequest nutrition, ConstraintValidatorContext context) {
    return nutrition != null &&
        AllowedNutrients.isValidNutrition(nutrition.name(), nutrition.unit());
  }
}