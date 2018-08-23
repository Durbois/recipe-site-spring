package com.teamtreehouse.recipesitespring.web.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.RecipeService;
import com.teamtreehouse.recipesitespring.service.UserService;
import com.teamtreehouse.recipesitespring.web.FlashMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private RecipeController controller;

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

    User userDao = new User(user.getUsername(), user.getPassword(), "ROLE_USER");
    when(userService.findByUsername(anyString())).thenReturn(userDao);

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @After
  public void tearDown () {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  @WithMockUser(username = "user", password = "password", roles = "ROLE_USER")
  public void showByDescription() throws Exception {
    List<Recipe> recipes = Arrays.asList(new Recipe("name", "description", "category", new byte[1],
    10, 12, new User("user", "password", "ROLE_USER")));

    when(recipeService.findAll()).thenReturn(recipes);

    mockMvc.perform(post("/recipe/search").param("search", "desc"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("categories", "user", "recipes"));

    verify(recipeService).findAll();
  }

  @Test
  public void showByCategory() throws Exception {
    List<Recipe> recipes = Arrays.asList(new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER")));

    when(recipeService.findAll()).thenReturn(recipes);

    mockMvc.perform(post("/recipe/category").param("category", "category"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("categories", "user", "recipes"));

    verify(recipeService).findAll();
  }

  @Test
  public void viewAll() throws Exception {
    List<Recipe> recipes = Arrays.asList(new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER")));

    when(recipeService.findAll()).thenReturn(recipes);

    mockMvc.perform(get("/recipe/all"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("categories", "user", "recipes"));

    verify(recipeService).findAll();
  }

  @Test
  public void favoriteRecipe() throws Exception {

    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(post("/recipe/favorite/1"))
        .andExpect(redirectedUrl("/recipe/detail/1"));

    verify(userService).save(any());
  }

  @Test
  public void detailRecipe() throws Exception {

    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/detail/1"))
        .andExpect(model().attributeExists("user", "recipe"))
        .andExpect(view().name("detail"));
  }

  @Test
  public void editRecipeNotWork() throws Exception {
    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/edit/1"))
        .andExpect(redirectedUrl("/profile"));
  }

  @Test
  public void editRecipeWork() throws Exception {
    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("username", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/edit/1"))
        .andExpect(view().name("edit"))
        .andExpect(model().attributeExists("user", "recipe", "heading"));
  }


  @Test
  public void deleteRecipeNotWork() throws Exception {
    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("user", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(delete("/recipe/delete/1"))
        .andExpect(redirectedUrl("/profile"))
        .andExpect(flash().attributeExists("flash"));

    verify(recipeService, never()).delete(any());
  }

  @Test
  public void deleteRecipeWork() throws Exception {
    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("username", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(delete("/recipe/delete/1"))
        .andExpect(redirectedUrl("/profile"))
        .andExpect(flash().attributeExists("flash"));

    verify(recipeService).delete(any());
  }

  @Test
  public void findImage() throws Exception {
    Recipe recipe = new Recipe("name", "description", "category", new byte[1],
        10, 12, new User("username", "password", "ROLE_USER"));

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/1.recipe"))
        .andExpect(status().isOk());
  }

  @Test
  public void buildRecipe() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());

    mockMvc.perform(fileUpload("/recipe/build").file(multipartFile));

    verify(recipeService, never()).buildRecipe(any());
  }

  @Test
  public void formNewRecipe() throws Exception {

    mockMvc.perform(get("/recipe/build"))
        .andExpect(view().name("edit"))
        .andExpect(model().attributeExists("user", "categories", "action"));
  }
}