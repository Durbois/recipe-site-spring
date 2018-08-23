package com.teamtreehouse.recipesitespring.service.impl;

import com.teamtreehouse.recipesitespring.dao.RecipeDao;
import com.teamtreehouse.recipesitespring.dao.UserDao;
import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

  @Autowired
  private RecipeDao recipeDao;

  @Autowired
  private UserDao userDao;

  @Override
  public Recipe findById(Long id) {
    return recipeDao.findById(id).get();
  }

  @Override
  public Recipe buildRecipe(Recipe recipe) {
    return recipeDao.save(recipe);
  }

  @Override
  public void delete(Recipe recipe) {
    recipeDao.delete(recipe);
  }

  @Override
  public void toggleFavorite(Recipe recipe, User user) {
    if (user.getFavoriteRecipes().contains(recipe)) {
      user.getFavoriteRecipes().remove(recipe);
    } else {
      user.addFavoriteRecipe(recipe);
    }
  }

  @Override
  public List<Recipe> findByAuthor(User author) {
    return recipeDao.findByAuthor(author);
  }

  @Override
  public List<Recipe> findAll() {
    return recipeDao.findAll();
  }
}
