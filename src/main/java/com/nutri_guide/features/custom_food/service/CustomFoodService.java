package com.nutri_guide.features.custom_food.service;

import java.util.UUID;
import com.nutri_guide.features.shared.dto.FoodRequest;
import com.nutri_guide.features.shared.dto.OwnedFoodView;
import com.nutri_guide.features.custom_food.dto.CustomFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFoodService {

  Page<OwnedFoodView> getAll(Pageable pageable, CustomFilterCriteria filterCriteria);

  OwnedFoodView getById(UUID id);

  OwnedFoodView create(FoodRequest dto);

  void delete(UUID id);

  OwnedFoodView update(UUID id, FoodRequest request);

  void deleteAllByUserId(UUID userId);

  boolean existsByUserIdAndFoodId(UUID id, UUID foodId);
}
