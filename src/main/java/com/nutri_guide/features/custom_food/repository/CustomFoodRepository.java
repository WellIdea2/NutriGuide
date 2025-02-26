package com.nutri_guide.features.custom_food.repository;

import java.util.Optional;
import java.util.UUID;
import com.nutri_guide.features.custom_food.entity.CustomFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFoodRepository extends JpaRepository<CustomFood, UUID>,
    JpaSpecificationExecutor<CustomFood> {

  Optional<CustomFood> findByIdAndUserId(UUID id, UUID userId);

  void deleteAllByUserId(UUID userId);

  boolean existsByIdAndUserId(UUID id, UUID userId);
}