package com.teamtreehouse.recipesitespring.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String condition;

  private Double quantity;

  public Ingredient(String name, String condition, Double quantity) {
    this.name = name;
    this.condition = condition;
    this.quantity = quantity;
  }

  public Ingredient()
  {
    this(null, null, null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ingredient that = (Ingredient) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(condition, that.condition) &&
        Objects.equals(quantity, that.quantity);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, condition, quantity);
  }
}