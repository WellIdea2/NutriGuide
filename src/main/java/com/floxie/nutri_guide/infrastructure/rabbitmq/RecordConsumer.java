package com.floxie.nutri_guide.infrastructure.rabbitmq;

import com.floxie.nutri_guide.features.meal.dto.MealRequest;
import com.floxie.nutri_guide.features.meal.services.MealService;
import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.entity.RecordDetails;
import com.floxie.nutri_guide.features.record.services.RecordService;
import com.floxie.nutri_guide.infrastructure.mappers.RecordMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commons.feature.shared.utils.GsonWrapper;
import org.commons.feature.user_details.dto.UserDetailsView;
import org.commons.rabbitmq.RabbitMqUserQueues;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordConsumer {

  private static final GsonWrapper GSON_WRAPPER = new GsonWrapper();
  private final RecordService recordService;
  private final MealService mealService;
  private final RecordMapper recordMapper;

  @RabbitListener(queues = RabbitMqUserQueues.USER_DETAILS_CREATION_NOTIFY_RECORD)
  public void recordFirstCreation(String userToken) {
    var userDetails = GSON_WRAPPER.fromJson(userToken, UserDetailsView.class);

    RecordCreateRequest recordCreateRequest = new RecordCreateRequest(LocalDate.now());

    var record = recordMapper.toEntity(recordCreateRequest);
    var recordDetails = new RecordDetails();

    recordDetails.setRecord(record);
    recordDetails.setKilograms(userDetails.kilograms());
    recordDetails.setAge(userDetails.age());
    recordDetails.setHeight(userDetails.height());
    recordDetails.setGender(userDetails.gender());
    recordDetails.setWorkoutState(userDetails.workoutState());

    record.setUserId(userDetails.userId());
    record.setRecordDetails(recordDetails);
    record.setDailyCalories(record.getCaloriesPerDay());

    var savedRecord = recordService.save(record);

    List<MealRequest> mealRequests =
        List.of(
            new MealRequest("Breakfast"),
            new MealRequest("Lunch"),
            new MealRequest("Dinner"),
            new MealRequest("Snack"));

    mealService.createMultiple(savedRecord, mealRequests);

    log.info("Record created for userDetails with id: {}", userDetails.id());
    log.info("Meals created for record with id: {}", savedRecord.getId());
  }

  @RabbitListener(queues = RabbitMqUserQueues.USER_DELETION_NOTIFY_RECORD)
  public void deleteUser(String message) {
    var userId = UUID.fromString(message);

    recordService.deleteAllByUserId(userId);

    log.info("Records deleted for user with id: {}", userId);
  }
}
