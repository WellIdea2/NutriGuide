package com.nutri_guide.features.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.commons.feature.shared.entity.BaseEntity;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true , exclude = "food")
@ToString(callSuper = true , exclude = "food")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nutrients")
public class Nutrient extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String unit;

  @Column(nullable = false)
  private Double amount;

  @ManyToOne
  private Food food;
}