package com.nutri_guide.infrastructure.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.nutri_guide.features.meal.dto.MealRequest;
import com.nutri_guide.features.meal.dto.MealView;
import com.nutri_guide.features.meal.entity.Meal;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(MealMapperDecoder.class)
public interface MealMapper {

  @Mapping(ignore = true , target = "foods")
  @Mapping(ignore = true, target = "consumedCalories")
  MealView toView(Meal entity);

  Meal toEntity(MealRequest dto);

  void update(@MappingTarget Meal entity, MealRequest dto);
}
