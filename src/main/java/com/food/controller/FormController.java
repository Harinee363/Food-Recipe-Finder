package com.food.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.food.entity.SavedEntity;
import com.food.entity.UserEntity;
import com.food.service.SavedRecipeService;
import com.food.service.UserService;  // Assuming you have a UserService to find users

@Controller
@RequestMapping("/recipe")
public class FormController {

    private final String apiKey = "917ce92e984b42ad80230007ef4877ce";

    private final RestTemplate restTemplate;
    private final SavedRecipeService savedRecipeService;
    private final UserService userService;  // Assuming a UserService for user-related operations

    public FormController(SavedRecipeService savedRecipeService, RestTemplate restTemplate, UserService userService) {
        this.savedRecipeService = savedRecipeService;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    // Show search form
    @GetMapping("/form")
    public String showForm() {
        return "search-form";  // => searchForm.jsp
    }

    // Process search and show results
    @GetMapping("/search")
    public String search(@RequestParam String ingredient, Model model) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + ingredient + "&addRecipeInformation=true&number=10&apiKey=" + apiKey;

        try {
            SpoonacularResponse response = restTemplate.getForObject(url, SpoonacularResponse.class);

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                model.addAttribute("recipes", response.getResults());
            } else {
                model.addAttribute("error", "No recipes found.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching recipes. Please try again later.");
        }

        model.addAttribute("ingredient", ingredient);
        return "searchResults";  // => searchResults.jsp
    }

    // Save a recipe to the database
    @GetMapping("/save")
    public String saveRecipe(@RequestParam String recipeId, Model model) {
        try {
            SavedEntity recipe = savedRecipeService.getById(Long.parseLong(recipeId)).orElseThrow();
            savedRecipeService.save(recipe);  // Save the recipe
            model.addAttribute("message", "Recipe saved successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error saving the recipe. Please try again later.");
        }

        return "redirect:/recipe/form";  // Redirect to the search form or appropriate page
    }

    @GetMapping("/details")
    public String getRecipeDetails(@RequestParam int id, Model model) {
        String url = "https://api.spoonacular.com/recipes/" + id + "/information?includeNutrition=false&apiKey=" + apiKey;

        try {
            SpoonacularResponse.Recipe recipe = restTemplate.getForObject(url, SpoonacularResponse.Recipe.class);
            model.addAttribute("recipe", recipe);
        } catch (Exception e) {
            model.addAttribute("error", "Could not fetch recipe details.");
        }

        return "recipeDetails";  // maps to recipeDetails.jsp
    }

    @PostMapping("/saveFavorites")
    public String saveRecipe(@RequestParam String recipeId,
                             @RequestParam String title,
                             @RequestParam String image,
                             @RequestParam int readyInMinutes,
                             Principal principal,
                             Model model) {

        // Get email from principal (since you're using email for login)
        String email = principal.getName();
        
        // Fetch the user by email instead of username
        UserEntity user = userService.getByEmail(email);  // Update here to find by email
        System.out.println("SAVE RECIPE CALLED");
        System.out.println("Recipe ID: " + recipeId + ", User Email: " + email);

        if (user == null) {
            // If the user is not found, return an error
            model.addAttribute("error", "User not found.");
            return "redirect:/recipe/form";  // Redirect to the form or another appropriate page
        }

        // Check if the recipe already exists in the user's saved recipes
        if (!savedRecipeService.existsByRecipeIdAndUser(recipeId, user)) {
            // If the recipe doesn't exist for the user, create a new SavedEntity and save it
            SavedEntity recipe = new SavedEntity();
            recipe.setRecipeId(recipeId);
            recipe.setTitle(title);
            recipe.setImageUrl(image);
            recipe.setReadyInMinutes(readyInMinutes);
            recipe.setUser(user);  // Associate with the current user

            try {
                savedRecipeService.save(recipe);  // Save the recipe to the database
                model.addAttribute("message", "Recipe saved to favorites successfully!");
            } catch (Exception e) {
                model.addAttribute("error", "Error saving the recipe. Please try again.");
                return "redirect:/recipe/form";  // Redirect to the form or an error page
            }
        } else {
            model.addAttribute("error", "This recipe is already saved in your favorites.");
        }

        return "redirect:/recipe/favorites";  // Redirect to the favorites page to see saved recipes
    }


    @GetMapping("/favorites")
    public String viewFavorites(Model model, Principal principal) {
        // Get email from principal (since you're using email for login)
        String email = principal.getName();
        
        // Fetch the user by email
        UserEntity user = userService.getByEmail(email);  // Update here to find by email
        
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/users/login";  // Redirect to login if the user is not found
        }

        // Fetch user's saved recipes
        List<SavedEntity> savedRecipes = savedRecipeService.findByUser(user);  // Fetch user's saved recipes
        model.addAttribute("savedRecipes", savedRecipes);

        return "Favorites";  // Maps to favorites.jsp (or whatever your favorites page is)
    }
}
