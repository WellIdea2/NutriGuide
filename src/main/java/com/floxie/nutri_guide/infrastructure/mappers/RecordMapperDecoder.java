package com.floxie.nutri_guide.infrastructure.mappers;

import com.floxie.nutri_guide.features.meal_food.entity.MealFood;
import com.floxie.nutri_guide.features.record.dto.RecordCreateRequest;
import com.floxie.nutri_guide.features.record.dto.RecordView;
import com.floxie.nutri_guide.features.record.entity.Record;
import com.floxie.nutri_guide.features.record.entity.RecordDetails;
import com.floxie.nutri_guide.features.record.utils.MacronutrientCreator;
import com.floxie.nutri_guide.features.record.utils.MineralCreator;
import com.floxie.nutri_guide.features.record.utils.VitaminCreator;
import com.floxie.nutri_guide.features.shared.dto.NutritionIntakeView;
import com.floxie.nutri_guide.features.shared.entity.Food;
import com.floxie.nutri_guide.features.shared.entity.Nutrient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class RecordMapperDecoder implements RecordMapper {

  private RecordMapper delegate;

  @Override
  public RecordView toView(Record record) {
    var recordView = delegate.toView(record);
    RecordDetails recordDetails = record.getRecordDetails();

    double consumedCalories = 0;

    Map<String, NutritionIntakeView> nutritionMap = new HashMap<>();

    MineralCreator.fillMinerals(nutritionMap, recordDetails.getGender(), recordDetails.getAge());
    VitaminCreator.fillVitamins(nutritionMap, recordDetails.getGender(), recordDetails.getAge());
    MacronutrientCreator.fillMacros(
        nutritionMap, record.getDailyCalories(), recordDetails.getGender(), recordDetails.getAge());

    List<MealFood> list =
        record.getMeals().stream().flatMap(meal -> meal.getFoods().stream()).toList();

    for (Food food : list) {
      fillNutrientsMap(nutritionMap, food.getNutrients());
      consumedCalories += food.getCalories().getAmount();
    }

    setVitaminIntakes(recordView, nutritionMap);
    setMineralsIntakes(recordView, nutritionMap);
    setMacrosIntakes(recordView, nutritionMap);

    recordView.setConsumedCalories(consumedCalories);
    recordView.setDailyCalories(record.getDailyCalories());

    return recordView;
  }

  @Autowired
  public void setDelegate(@Qualifier("delegate") RecordMapper delegate) {
    this.delegate = delegate;
  }

  private void fillNutrientsMap(
      Map<String, NutritionIntakeView> nutationsMap, List<Nutrient> macronutrients) {
    for (Nutrient nutrient : macronutrients) {
      NutritionIntakeView nutritionIntake = nutationsMap.get(nutrient.getName());
      nutritionIntake.setDailyConsumed(nutritionIntake.getDailyConsumed() + nutrient.getAmount());
    }
  }

  private void setVitaminIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setVitaminIntake(
        intakeViewMap.values().stream()
            .filter(
                nutritionIntakeView ->
                    VitaminCreator.allAllowedVitamins.contains(nutritionIntakeView.getName()))
            .toList());
  }

  private void setMineralsIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setMineralIntakes(
        intakeViewMap.values().stream()
            .filter(
                nutritionIntakeView ->
                    MineralCreator.allAllowedMinerals.contains(nutritionIntakeView.getName()))
            .toList());
  }

  private void setMacrosIntakes(RecordView view, Map<String, NutritionIntakeView> intakeViewMap) {
    view.setMacroIntakes(
        intakeViewMap.values().stream()
            .filter(
                nutritionIntakeView ->
                    MacronutrientCreator.allAllowedMacros.contains(nutritionIntakeView.getName()))
            .toList());
  }
}
