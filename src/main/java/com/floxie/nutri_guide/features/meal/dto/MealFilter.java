package com.floxie.nutri_guide.features.meal.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealFilter {

  private UUID recordId;
  private UUID userId;
}