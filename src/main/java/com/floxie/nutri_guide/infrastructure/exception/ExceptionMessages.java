package com.floxie.nutri_guide.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.commons.exceptions.ExceptionMessage;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages implements ExceptionMessage {
  INVALID_USER_TOKEN("Invalid user token", "Invalid user token"),
  INVALID_FOOD_TYPE("Invalid food type. Valid ones: branded , survey , final", "Invalid food type"),
  FOOD_NOT_FOUND("Food with id %s not found", "Food not found"),
  NUTRITION_NOT_FOUND_WITH_NAME("Nutrition with name %s not found", "Nutrition not found"),
  NUTRITIONIX_NOT_FOUND("Nutritionix API returned no results", "Nutritionix not found"),
  RECORD_NOT_FOUND("Record with id %s not found", "Record not found"),
  MEAL_NOT_FOUND("Meal with id %s not found", "Meal not found"),
  RECORD_ALREADY_EXISTS("Record already exists for this date", "Record already exists"),
  MEAL_NOT_FOUND_WITH_RECORD_AND_USER("Meal with ID: %s not found with record id: %s and user id: %s", "Storage not found with record and user");;

  private final String message;
  private final String title;
}
