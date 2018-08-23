package com.teamtreehouse.recipesitespring.config;

import com.teamtreehouse.recipesitespring.dao.IngredientDao;
import com.teamtreehouse.recipesitespring.dao.StepDao;
import com.teamtreehouse.recipesitespring.dao.RecipeDao;
import com.teamtreehouse.recipesitespring.dao.UserDao;
import com.teamtreehouse.recipesitespring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {

  private final UserDao userDao;
  private final RecipeDao recipeDao;
  private final StepDao stepDao;
  private final IngredientDao ingredientDao;

  @Autowired
  public DatabaseLoader (UserDao userDao,RecipeDao recipeDao, StepDao stepDao,
                         IngredientDao ingredientDao) {
    this.userDao = userDao;
    this.recipeDao = recipeDao;
    this.stepDao = stepDao;
    this.ingredientDao = ingredientDao;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
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
  }
}
