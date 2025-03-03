package com.floxie.nutri_guide.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.commons.exceptions.ExceptionMessage;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages implements ExceptionMessage {
  INVALID_USER_TOKEN("Invalid user token", "Invalid user token"),
  FOOD_NOT_FOUND("Food with id %s not found", "Food not found"),
  NUTRITIONIX_NOT_FOUND("Nutritionix API returned no results", "Nutritionix not found"),
  RECORD_NOT_FOUND("Record with id %s not found", "Record not found"),
  MEAL_NOT_FOUND("Meal with id %s not found", "Meal not found");

  private final String message;
  private final String title;
}
