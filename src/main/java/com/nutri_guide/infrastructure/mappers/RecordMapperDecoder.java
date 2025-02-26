package com.nutri_guide.infrastructure.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nutri_guide.features.meal_food.entity.MealFood;
import com.nutri_guide.features.shared.dto.NutritionIntakeView;
import org.commons.feature.user.dto.UserView;
import com.nutri_guide.features.shared.entity.Food;
import com.nutri_guide.features.shared.entity.Nutrient;
import com.nutri_guide.features.record.dto.RecordCreateRequest;
import com.nutri_guide.features.record.dto.RecordView;
import com.nutri_guide.features.record.entity.Record;
import com.nutri_guide.features.record.entity.UserDetails;
import com.nutri_guide.features.record.utils.MacronutrientCreator;
import com.nutri_guide.features.record.utils.MineralCreator;
import com.nutri_guide.features.record.utils.RecordUtils;
import com.nutri_guide.features.record.utils.VitaminCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class RecordMapperDecoder implements RecordMapper {

  private RecordMapper delegate;

  @Override
  public Record toEntity(RecordCreateRequest dto, UserView user) {
    Record entity = delegate.toEntity(dto, user);
    UserDetails userDetails = new UserDetails();

    userDetails.setAge(user.age());
    userDetails.setGender(user.gender());
    userDetails.setHeight(user.height());
    userDetails.setKilograms(user.kilograms());
    userDetails.setWorkoutState(user.workoutState());
    userDetails.setRecord(entity);

    Double BMR = RecordUtils.getBmr(userDetails);
    Double caloriesPerDay = RecordUtils.getCaloriesPerDay(userDetails, BMR);

    entity.setDailyCalories(caloriesPerDay);
    entity.setUserDetails(userDetails);

    return entity;
  }

  @Override
  public RecordView toView(Record record) {
    var recordView = delegate.toView(record);
    UserDetails userDetails = record.getUserDetails();

    double consumedCalories = 0;

    Map<String, NutritionIntakeView> nutritionMap = new HashMap<>();

    MineralCreator.fillMinerals(nutritionMap, userDetails.getGender(), userDetails.getAge());
    VitaminCreator.fillVitamins(nutritionMap, userDetails.getGender(), userDetails.getAge());
    MacronutrientCreator.fillMacros(nutritionMap, record.getDailyCalories(),
        userDetails.getGender(),
        userDetails.getAge());

    List<MealFood> list = record.getMeals()
        .stream()
        .flatMap(meal -> meal.getFoods().stream())
        .toList();

    for (Food food : list) {
      fillNutrientsMap(nutritionMap, food.getNutrients());
      consumedCalories += food.getCalories().getAmount();
    }

    setVitaminIntakes(recordView, nutritionMap);
    setMineralsIntakes(recordView, nutritionMap);
    setMacrosIntakes(recordView, nutritionMap);

    recordView.setConsumedCalories(consumedCalories);

    return recordView;
  }

  @Autowired
  public void setDelegate(@Qualifier("delegate") RecordMapper delegate) {
    this.delegate = delegate;
  }


  private void fillNutrientsMap(Map<String, NutritionIntakeView> nutationsMap,
      List<Nutrient> macronutrients) {
    for (Nutrient nutrient : macronutrients) {
      NutritionIntakeView nutritionIntake = nutationsMap.get(nutrient.getName());
      nutritionIntake.setDailyConsumed(
          nutritionIntake.getDailyConsumed() + nutrient.getAmount());
    }
  }

  private void setVitaminIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setVitaminIntake(
        intakeViewMap.values()
            .stream()
            .filter(nutritionIntakeView -> VitaminCreator.allAllowedVitamins.contains(
                nutritionIntakeView.getName()))
            .toList());
  }

  private void setMineralsIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setMineralIntakes(
        intakeViewMap.values()
            .stream()
            .filter(nutritionIntakeView -> MineralCreator.allAllowedMinerals.contains(
                nutritionIntakeView.getName()))
            .toList());
  }

  private void setMacrosIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setMacroIntakes(
        intakeViewMap.values()
            .stream()
            .filter(nutritionIntakeView -> MacronutrientCreator.allAllowedMacros.contains(
                nutritionIntakeView.getName()))
            .toList());
  }
}
