package com.floxie.nutri_guide.features.meal.services;

import static com.floxie.nutri_guide.infrastructure.exception.ExceptionMessages.MEAL_NOT_FOUND;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.commons.exceptions.throwable.NotFoundException;
import com.floxie.nutri_guide.features.meal.dto.MealRequest;
import com.floxie.nutri_guide.features.meal.dto.MealView;
import com.floxie.nutri_guide.features.meal.entity.Meal;
import com.floxie.nutri_guide.features.meal.repository.MealRepository;
import com.floxie.nutri_guide.features.meal.repository.MealSpecification;
import com.floxie.nutri_guide.features.record.entity.Record;
import com.floxie.nutri_guide.features.record.services.RecordService;
import com.floxie.nutri_guide.infrastructure.config.security.SecurityUtils;
import com.floxie.nutri_guide.infrastructure.mappers.MealMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MealServiceImp implements MealService{

  private final RecordService recordService;
  private final MealRepository mealRepository;
  private final MealMapper mealMapper;

  public Page<MealView> getAll(UUID recordId, Pageable pageable) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return mealRepository.findAll(new MealSpecification(user, recordId), pageable)
        .map(mealMapper::toView);
  }

  public MealView getById(UUID mealId) {
    return mealMapper.toView(findById(mealId));
  }

  public MealView create(UUID recordId, MealRequest dto) {
    var user = SecurityUtils.getCurrentLoggedInUser();
    var record = recordService.findByIdAndUserId(recordId, user.id());

    var entity = mealMapper.toEntity(dto);
    entity.setRecord(record);

    return mealMapper.toView(mealRepository.save(entity));
  }

  public MealView update(UUID mealId, MealRequest dto) {
    var meal = findById(mealId);

    mealMapper.update(meal, dto);

    return mealMapper.toView(mealRepository.save(meal));
  }

  public void delete(UUID mealId) {
    if (!mealRepository.existsById(mealId)) {
      throw new NotFoundException(MEAL_NOT_FOUND, mealId);
    }

    mealRepository.deleteById(mealId);
  }

  public Meal findByIdAndUserId(UUID storageId, UUID userId) {
    return mealRepository.findByIdAndUserId(storageId, userId)
        .orElseThrow(() -> new NotFoundException(MEAL_NOT_FOUND, storageId));
  }

  public void createMultiple(Record record, List<MealRequest> dtos) {
    mealRepository.saveAll(dtos.stream()
        .map(mealMapper::toEntity)
        .peek(meal -> meal.setRecord(record))
        .toList()
    );
  }

  public boolean isOwner(UUID mealId, UUID userId) {
    return mealRepository.existsByIdAndUserId(mealId, userId);
  }

  private Meal findById(UUID mealId) {
    return mealRepository.findById(mealId)
        .orElseThrow(() -> new NotFoundException(MEAL_NOT_FOUND, mealId));
  }
}
