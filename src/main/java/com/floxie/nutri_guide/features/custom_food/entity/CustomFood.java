package com.floxie.nutri_guide.features.custom_food.entity;

import com.floxie.nutri_guide.features.shared.entity.Food;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("CUSTOM")
public class CustomFood extends Food {

}