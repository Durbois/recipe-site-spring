package com.teamtreehouse.recipesitespring.dao;

import com.teamtreehouse.recipesitespring.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
  User findByUsername(String username);

  List<User> findAll();
}
