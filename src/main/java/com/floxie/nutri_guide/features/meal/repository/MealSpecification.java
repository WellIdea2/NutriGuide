package com.floxie.nutri_guide.features.meal.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.feature.user.dto.UserView;
import com.floxie.nutri_guide.features.meal.entity.Meal;
import org.commons.feature.user.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class MealSpecification implements Specification<Meal> {

  private final UserView user;
  private final UUID recordId;

  @Override
  public Predicate toPredicate(@NonNull Root<Meal> root, @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (UserRole.ADMIN != user.role()) {
      predicates.add(criteriaBuilder.equal(root.get("record").get("userId"), user.id()));
    }

    if (recordId != null) {
      predicates.add(criteriaBuilder.equal(root.get("record").get("id"), recordId));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
