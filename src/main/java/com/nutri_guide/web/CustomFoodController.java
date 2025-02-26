package com.nutri_guide.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.nutri_guide.paths.CustomFoodControllerPaths;
import com.nutri_guide.features.custom_food.dto.CustomFilterCriteria;
import com.nutri_guide.features.custom_food.service.CustomFoodService;
import com.nutri_guide.features.shared.dto.FoodRequest;
import com.nutri_guide.features.shared.dto.OwnedFoodView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(CustomFoodControllerPaths.BASE)
@RequiredArgsConstructor
public class CustomFoodController {

  private final CustomFoodService foodService;

  @GetMapping(CustomFoodControllerPaths.GET_ALL)
  public ResponseEntity<Page<OwnedFoodView>> getAll(Pageable pageable,
      CustomFilterCriteria filterCriteria) {
    return ResponseEntity.ok(foodService.getAll(pageable, filterCriteria));
  }

  @GetMapping(CustomFoodControllerPaths.GET_BY_ID)
  @PreAuthorize("@customFoodEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<OwnedFoodView> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(foodService.getById(id));
  }

  @PostMapping(CustomFoodControllerPaths.CREATE)
  public ResponseEntity<OwnedFoodView> create(
      @Valid @RequestBody FoodRequest createCustomFood) {
    return ResponseEntity.ok(foodService.create(createCustomFood));
  }

  @PutMapping(CustomFoodControllerPaths.UPDATE)
  @PreAuthorize("@customFoodEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<OwnedFoodView> update(@Valid @RequestBody FoodRequest updateCustomFood,
      @PathVariable(name = "id") UUID id) {
    return ResponseEntity.ok(foodService.update(id, updateCustomFood));
  }

  @DeleteMapping(CustomFoodControllerPaths.DELETE)
  @PreAuthorize("@customFoodEvaluator.isOwner(#id) || hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
    foodService.delete(id);
    return ResponseEntity.ok().build();
  }
}