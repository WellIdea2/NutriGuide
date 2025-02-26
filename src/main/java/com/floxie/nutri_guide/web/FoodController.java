package com.floxie.nutri_guide.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.floxie.nutri_guide.features.shared.dto.FoodRequest;
import com.floxie.nutri_guide.features.shared.dto.OwnedFoodView;
import org.commons.feature.nutri_guide.paths.MealFoodControllerPaths;
import com.floxie.nutri_guide.features.meal_food.dto.FoodFilter;
import com.floxie.nutri_guide.features.meal_food.service.MealFoodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MealFoodControllerPaths.BASE)
@RequiredArgsConstructor
public class FoodController {

  private final MealFoodService service;

  @GetMapping(MealFoodControllerPaths.GET_ALL)
  public ResponseEntity<Page<OwnedFoodView>> getAll(
      @PathVariable UUID mealId, Pageable pageable, @Valid FoodFilter filter) {
    return new ResponseEntity<>(
        service.getAll(mealId, filter, pageable),
        HttpStatus.OK);
  }

  @GetMapping(MealFoodControllerPaths.GET_BY_ID)
  @PreAuthorize("@foodEvaluator.isOwner(#mealId, #foodId) || hasRole('ADMIN')")
  public ResponseEntity<OwnedFoodView> get(@PathVariable UUID mealId, @PathVariable UUID foodId) {
    return new ResponseEntity<>(
        service.get(foodId, mealId),
        HttpStatus.OK);
  }

  @PostMapping(MealFoodControllerPaths.CREATE)
  public ResponseEntity<OwnedFoodView> create(@PathVariable UUID mealId,
      @RequestBody @Valid FoodRequest dto) {
    return new ResponseEntity<>(
        service.create(mealId, dto),
        HttpStatus.CREATED);
  }

  @PutMapping(MealFoodControllerPaths.UPDATE)
  @PreAuthorize("@foodEvaluator.isOwner(#mealId, #foodId) || hasRole('ADMIN')")
  public ResponseEntity<OwnedFoodView> update(@PathVariable UUID mealId, @PathVariable UUID foodId,
      @RequestBody @Valid FoodRequest dto) {
    return new ResponseEntity<>(
        service.update(mealId, foodId, dto),
        HttpStatus.OK);
  }

  @DeleteMapping(MealFoodControllerPaths.DELETE)
  @PreAuthorize("@foodEvaluator.isOwner(#mealId, #foodId) || hasRole('ADMIN')")
  public ResponseEntity<Void> delete(
      @PathVariable UUID mealId, @PathVariable UUID foodId) {
    service.delete(mealId, foodId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}