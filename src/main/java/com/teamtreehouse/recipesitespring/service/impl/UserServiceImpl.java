package com.teamtreehouse.recipesitespring.service.impl;

import com.teamtreehouse.recipesitespring.dao.UserDao;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @Override
  public User save(User user) {
    return userDao.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Unable to find user with username " + username);
    }

    org.springframework.security.core.userdetails.User
        userDetails = new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        AuthorityUtils.createAuthorityList(user.getRole()));

    return userDetails;
  }
}
