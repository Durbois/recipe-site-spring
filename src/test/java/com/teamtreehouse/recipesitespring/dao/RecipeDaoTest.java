package com.teamtreehouse.recipesitespring.dao;

import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeDaoTest {

  @Autowired
  private RecipeDao recipeDao;

  @Autowired
  private UserDao userDao;

  @Before
  public void setUp() throws Exception {
    User user1 = new User("User1", "password", "ROLE_USER");
    userDao.save(user1);
    User user2 = new User("User2", "password", "ROLE_USER");
    userDao.save(user2);
    User user3 = new User("User3", "password", "ROLE_USER");
    userDao.save(user3);
    User user4 = new User("User4", "password", "ROLE_USER");
    userDao.save(user4);
    User user5 = new User("User5", "password", "ROLE_USER");
    userDao.save(user5);

    Recipe recipe1 = new Recipe("recipe1", "description1", "category1", new byte[1], 12, 12, user1);
    recipeDao.save(recipe1);
    Recipe recipe2 = new Recipe("recipe2", "description2", "category2", new byte[1], 12, 12, user1);
    recipeDao.save(recipe2);
  }

  @Test
  public void findByName() {
    Recipe recipe = recipeDao.findByName("recipe1");

    assertNotNull(recipe);
  }

  @Test
  public void findAll() {

    List<Recipe> recipes = recipeDao.findAll();

    assertTrue(recipes.size() == 2);
  }

  @Test
  public void findByAuthor() {
    User user = userDao.findByUsername("User1");
    Recipe recipe = recipeDao.findByAuthor(user).get(0);

    assertNotNull(recipe);
  }
}