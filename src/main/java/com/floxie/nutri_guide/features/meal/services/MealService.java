package com.floxie.nutri_guide.features.meal.services;

import java.util.List;
import java.util.UUID;
import com.floxie.nutri_guide.features.meal.dto.MealRequest;
import com.floxie.nutri_guide.features.meal.dto.MealView;
import com.floxie.nutri_guide.features.meal.entity.Meal;
import com.floxie.nutri_guide.features.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MealService {

  Page<MealView> getAll(UUID recordId, Pageable pageable);

  MealView getById(UUID mealId);

  MealView create(UUID recordId, MealRequest dto);

  MealView update(UUID mealId, MealRequest dto);

  void delete(UUID mealId);

  Meal findByIdAndUserId(UUID storageId, UUID userId);

  void createMultiple(Record record, List<MealRequest> dtos);

  boolean isOwner(UUID mealId, UUID userId);
}