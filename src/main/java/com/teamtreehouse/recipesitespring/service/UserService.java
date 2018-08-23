package com.teamtreehouse.recipesitespring.service;

import com.teamtreehouse.recipesitespring.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
  User findByUsername(String username);

  User save(User user);

  List<User> findAll();
}
