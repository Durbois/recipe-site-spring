package com.teamtreehouse.recipesitespring.dao;

import com.teamtreehouse.recipesitespring.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientDao extends CrudRepository<Ingredient, Long> {
}
