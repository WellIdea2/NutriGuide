package com.floxie.nutri_guide.infrastructure.mappers;

import java.util.List;
import java.util.Optional;
import com.floxie.nutri_guide.features.shared.dto.ServingRequest;
import com.floxie.nutri_guide.features.shared.dto.ServingView;
import com.floxie.nutri_guide.features.shared.entity.Food;
import com.floxie.nutri_guide.features.shared.entity.Serving;
import com.floxie.nutri_guide.infrastructure.nutritionix.dto.FoodItem;

public class ServingMapper {

  private ServingMapper() {
  }

  public static List<ServingView> getServings(FoodItem dto) {

    return Optional.ofNullable(dto.getMeasures())
        .map(list -> list
            .stream()
            .map(measure -> new ServingView(
                Optional.ofNullable(measure.getQty())
                    .orElse(1.0),
                Optional.ofNullable(measure.getServingWeight())
                    .orElse(100.0),
                Optional.ofNullable(measure.getMeasure())
                    .orElse("g"))
            )
            .toList())
        .orElse(List.of());
  }

  public static ServingView getMainServing(FoodItem dto) {

    if (dto.getBrandName() == null) {
      return new ServingView(
          Optional.ofNullable(dto.getServingQty())
              .orElse(1.0),
          Optional.ofNullable(dto.getServingWeightGrams())
              .orElse(100.0),
          Optional.ofNullable(dto.getServingUnit())
              .orElse("g"));
    }
    return new ServingView(
        Optional.ofNullable(dto.getServingQty())
            .orElse(1.0),
        Optional.ofNullable(dto.getServingWeightGrams())
            .orElse(
                Optional.ofNullable(dto.getNfMetricQty())
                    .orElse(100.0)
            ),
        Optional.ofNullable(dto.getServingUnit())
            .orElse("g"));
  }

  public static ServingView toView(Serving serving) {
    return new ServingView(
        serving.getAmount(),
        serving.getServingWeight(),
        serving.getMetric()
    );
  }

  public static Serving toEntity(ServingRequest dto, Food food) {
    var serving = new Serving();
    serving.setAmount(dto.amount());
    serving.setServingWeight(dto.servingWeight());
    serving.setMetric(dto.metric());
    serving.setFood(food);
    return serving;
  }
}
