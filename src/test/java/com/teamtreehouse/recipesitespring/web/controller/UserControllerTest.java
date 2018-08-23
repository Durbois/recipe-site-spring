package com.teamtreehouse.recipesitespring.web.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.RecipeService;
import com.teamtreehouse.recipesitespring.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private UserController controller;

  @Mock
  private UserService userService;

  @Mock
  private RecipeService recipeService;

  @Before
  public void setUp() {
    org.springframework.security.core.userdetails.User user = new
        org.springframework.security.core.userdetails.User("username",
        "password", AuthorityUtils.createAuthorityList("ROLE_USER"));

    Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
    SecurityContextHolder.getContext().setAuthentication(auth);

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @After
  public void tearDown () {
    SecurityContextHolder.getContext().setAuthentication(null);
  }


  @Test
  @Ignore
  public void signUp() throws Exception {

    mockMvc.perform(get("/signup"));
  }

  @Test
  public void registerUser() throws Exception  {

   mockMvc.perform(post("/signup"));
  }

  @Test
  @Ignore
  public void loginForm() throws Exception {

    mockMvc.perform(get("/login"));
  }

  @Test
  @Ignore
  public void profile() throws Exception {

    when(recipeService.findByAuthor(any())).thenReturn(Collections.singletonList(new Recipe()));

    mockMvc.perform(get("/profile"));
  }
}