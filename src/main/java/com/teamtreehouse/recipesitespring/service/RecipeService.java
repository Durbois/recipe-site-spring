package com.teamtreehouse.recipesitespring.service;

import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;

import java.util.List;

public interface RecipeService {

  Recipe buildRecipe(Recipe recipe);

  Recipe findById (Long id);

  void delete (Recipe recipe);

  void toggleFavorite(Recipe recipe, User user);

  List<Recipe> findAll();

  List<Recipe> findByAuthor (User author);
}
