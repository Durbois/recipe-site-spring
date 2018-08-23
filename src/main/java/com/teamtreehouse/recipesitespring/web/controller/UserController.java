package com.teamtreehouse.recipesitespring.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RecipeService recipeService;

  private User findLoggedUser () {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User userDao = userService.findByUsername(user.getUsername());

    return userDao;
  }

  @RequestMapping(path = "/signup", method = RequestMethod.GET)
  public String signUp (Model model) {

    User user = new User();
    model.addAttribute("user", user);
    model.addAttribute("action", "/signup");

    return "signup";
  }

  @RequestMapping(path = "/signup", method = RequestMethod.POST)
  public String registerUser (RedirectAttributes redirectAttributes, Model model,
                              User user, @RequestParam String password, @RequestParam String passwordAgain ) {

    User existUser = userService.findByUsername(user.getUsername());
    if (existUser != null) {
      redirectAttributes.addFlashAttribute("flash", new FlashMessage("User already exist!",
          FlashMessage.Status.FAILURE));

      return "redirect:/signup";
    }

    if (!password.equals(passwordAgain)) {
      redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sign Up failed, please try again",
      FlashMessage.Status.FAILURE));

     return "redirect:/signup";
    }

    user.setRole("ROLE_USER");
    userService.save(user);

    return "redirect:/login";
  }

  @RequestMapping(path = "/login", method = RequestMethod.GET)
  public String loginForm(Model model, HttpServletRequest request) {
    model.addAttribute("user", new User());
    try {
      Object flash = request.getSession().getAttribute("flash");
      model.addAttribute("flash", flash);

      request.getSession().removeAttribute("flash");
    } catch (Exception ex) {
      // "flash" session attribute must not exist...do nothing and proceed normally
    }
    return "login";
  }

  @RequestMapping(path = "/profile", method = RequestMethod.GET)
  @PreAuthorize(value = "hasAnyRole('ROLE_USER')")
  public String profile (Model model) {

    User user = findLoggedUser ();
    List<Recipe> recipeList = recipeService.findByAuthor(user);

    model.addAttribute("user", user);
    model.addAttribute("recipes", recipeList);

    return "profile";
  }
}
