package com.teamtreehouse.recipesitespring.dao;

import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDao extends CrudRepository<Recipe, Long> {

  Recipe findByName (String name);

  List<Recipe> findAll();

  List<Recipe> findByAuthor (User author);
}
