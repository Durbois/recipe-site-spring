package com.teamtreehouse.recipesitespring.service;

import static org.mockito.Mockito.*;

import com.teamtreehouse.recipesitespring.dao.UserDao;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserDao userDao;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  @Test
  public void findByUsername() {

    User user = userService.findByUsername("User");
    verify(userDao).findByUsername(anyString());
  }

  @Test
  public void save() {
    User user = userService.save(any());

    verify(userDao).save(any());
  }

  @Test
  public void findAll() {
    List<User> users = userService.findAll();

    verify(userDao).findAll();
  }
}