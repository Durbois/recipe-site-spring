package com.teamtreehouse.recipesitespring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Column(nullable = false, unique = true)
  private String username;

  @JsonIgnore
  private String password;

  @JsonIgnore
  private String role;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Recipe> recipes = new ArrayList<>();

  @ManyToMany
  private List<Recipe> favoriteRecipes = new ArrayList<>();

  public User() {
    super();
    recipes = new ArrayList<>();
    favoriteRecipes = new ArrayList<>();
  }

  public User(String username, String password, String role) {
    this();
    this.username = username;
    setPassword(password);
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = PASSWORD_ENCODER.encode(password);
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }

  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }

  public void removeRecipe(Recipe recipe) {
    recipes.remove(recipe);
  }

  public List<Recipe> getFavoriteRecipes() {
    return favoriteRecipes;
  }

  public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
    this.favoriteRecipes = favoriteRecipes;
  }

  public void addFavoriteRecipe (Recipe recipe) {
    favoriteRecipes.add(recipe);
  }

  public void removeFavoriteRecipe (Recipe recipe) {
    favoriteRecipes.remove(recipe);
  }

  @Override
  public String toString() {
    return "User{" +
        ", username='" + username + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(username);
  }
}