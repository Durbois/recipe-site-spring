package com.teamtreehouse.recipesitespring.dao;

import static org.junit.Assert.*;
import com.teamtreehouse.recipesitespring.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

  @Autowired
  private UserDao userDao;

  @Before
  public void setUp () {
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

  @Test
  public void findByUsername() {

    User user = userDao.findByUsername("User1");

    assertNotNull(user);
  }

  @Test
  public void findAll() {

    List<User> userList = userDao.findAll();

    assertTrue(userList.size() == 5);
  }
}