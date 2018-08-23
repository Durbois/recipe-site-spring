package com.teamtreehouse.recipesitespring.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import com.teamtreehouse.recipesitespring.dao.RecipeDao;
import com.teamtreehouse.recipesitespring.dao.UserDao;
import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.impl.RecipeServiceImpl;
import com.teamtreehouse.recipesitespring.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

  @Mock
  UserDao userDao;

  @Mock
  RecipeDao recipeDao;

  @InjectMocks
  RecipeService recipeService = new RecipeServiceImpl();

  @InjectMocks
  UserService userService = new UserServiceImpl();

  @Test
  public void buildRecipe() {
    Recipe recipe = recipeService.buildRecipe(any());
    verify(recipeDao).save(any());
  }

  @Test
  public void findById() {

    Recipe recipe = new Recipe();
    Optional<Recipe> opt = Optional.of(recipe);
    when(recipeDao.findById(anyLong())).thenReturn(opt);

    recipe = recipeService.findById(anyLong());

    verify(recipeDao).findById(any());
  }

  @Test
  public void delete() {
    recipeService.delete(any());
    verify(recipeDao).delete(any());
  }

  @Test
  public void toggleFavorite() {
    User user = new User();
    Recipe recipe = new Recipe();

    user.addFavoriteRecipe(recipe);

    recipeService.toggleFavorite(recipe, user);
    assertTrue(user.getFavoriteRecipes().size() == 0);
  }

  @Test
  public void findAll() {
    List<Recipe> recipeList = recipeService.findAll();

    verify(recipeDao).findAll();
  }

  @Test
  public void findByAuthor() {
    User user = new User();

    recipeService.findByAuthor(user);

    verify(recipeDao).findByAuthor(user);
  }
}