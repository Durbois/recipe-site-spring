package com.teamtreehouse.recipesitespring.web.controller;

import com.teamtreehouse.recipesitespring.domain.Category;
import com.teamtreehouse.recipesitespring.domain.Ingredient;
import com.teamtreehouse.recipesitespring.domain.Step;
import com.teamtreehouse.recipesitespring.domain.Recipe;
import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.RecipeService;
import com.teamtreehouse.recipesitespring.service.UserService;
import com.teamtreehouse.recipesitespring.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/recipe")
public class RecipeController {

  @Autowired
  private RecipeService recipeService;

  @Autowired
  private UserService userService;

  List<Ingredient> ingredientList = new ArrayList<>();

  private User findLoggedUser () {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User userDao = userService.findByUsername(user.getUsername());

    return userDao;
  }

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String showByDescription (Model model, @RequestParam String search, RedirectAttributes redirectAttributes) {

    List<Recipe> recipes = recipeService.findAll();

    recipes = recipes.stream()
        .filter(recipe -> recipe.getDescription().contains(search))
        .collect(Collectors.toList());

    model.addAttribute("categories", Category.values());
    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipes", recipes);

    return "index";
  }

  @RequestMapping(value = "/category", method = RequestMethod.POST)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String showByCategory (Model model, @RequestParam String category, RedirectAttributes redirectAttributes) {

    List<Recipe> recipes = recipeService.findAll();

    recipes = recipes.stream()
        .filter(recipe -> category.equals(recipe.getCategory()))
        .collect(Collectors.toList());

    model.addAttribute("categories", Category.values());
    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipes", recipes);

    return "index";
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String viewAll (Model model) {

    List<Recipe> recipes = recipeService.findAll();

    model.addAttribute("categories", Category.values());
    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipes", recipes);

    return "index";
  }

  @RequestMapping(value = "/favorite/{id}", method = RequestMethod.POST)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String favoriteRecipe (@PathVariable Long id) {

    Recipe recipe = recipeService.findById(id);
    User user = findLoggedUser();

    recipeService.toggleFavorite(recipe, user);

    userService.save(user);

    return String.format("redirect:/recipe/detail/%d", id);
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String detailRecipe (Model model, @PathVariable Long id) {

    Recipe recipe = recipeService.findById(id);

    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipe", recipe);

    return "detail";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String editRecipe (RedirectAttributes redirectAttributes, Model model, @PathVariable Long id) {

    Recipe recipe = recipeService.findById(id);

    if (!findLoggedUser().getUsername().equals(recipe.getAuthor().getUsername())) {
      redirectAttributes.addFlashAttribute("flash", new FlashMessage("You cannot edit this one, you don't own it",
          FlashMessage.Status.FAILURE));

      return "redirect:/profile";
    }

    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipe", recipe);
    model.addAttribute("heading", "Edit Recipe");
    model.addAttribute("categories", Category.values());
    model.addAttribute("action", "/recipe/build");
    model.addAttribute("submit", "Save Recipe");

    return "edit";
  }

  @RequestMapping(value = "/delete/{id}")
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String deleteRecipe (RedirectAttributes redirectAttributes, @PathVariable Long id) {

    Recipe recipe = recipeService.findById(id);

    if (!findLoggedUser().getUsername().equals(recipe.getAuthor().getUsername())) {
      redirectAttributes.addFlashAttribute("flash", new FlashMessage("You cannot delete this one, you don't own it",
          FlashMessage.Status.FAILURE));

      return "redirect:/profile";
    }

    recipeService.delete(recipe);
    redirectAttributes.addFlashAttribute("flash", new FlashMessage("You have deleted the recipe successfully",
        FlashMessage.Status.SUCCESS));
    return "redirect:/profile";
  }


  @RequestMapping(value = "/{id}.recipe", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  @ResponseBody
  public byte[] findImage (@PathVariable Long id) {

    return recipeService.findById(id).getImage();
  }

  @RequestMapping(value = "/build", method = RequestMethod.POST)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String buildRecipe (@RequestParam MultipartFile imageFile, Recipe recipe) {

    recipe.setAuthor(findLoggedUser());

    try {
      byte[] imageByte = imageFile.getBytes();
      recipe.setImage(imageByte);
    } catch (IOException e) {
      System.out.println("No Image available");
    }

    recipeService.buildRecipe(recipe);

    return "redirect:/profile";
  }

  @RequestMapping(value = "/build", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String formNewRecipe (Model model) {

    Recipe recipe = new Recipe();
    recipe.getIngredients().add(new Ingredient());
    recipe.getSteps().add(new Step());

    model.addAttribute("user", findLoggedUser());
    model.addAttribute("recipe", recipe);
    model.addAttribute("heading", "Build Recipe");
    model.addAttribute("categories", Category.values());
    model.addAttribute("action", "/recipe/build");
    model.addAttribute("submit", "Build Recipe");

    return "edit";
  }
}
