package com.food.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.food.entity.SavedEntity;

import com.food.service.SavedRecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final SavedRecipeService service;
    private final RestTemplate restTemplate;
    private final String apiKey = "917ce92e984b42ad80230007ef4877ce";

    public RecipeController(SavedRecipeService service) {
        this.service = service;
        this.restTemplate = new RestTemplate();
    }

    //Fetch from Spoonacular API
    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String ingredient) {
        String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredient + "&number=5&apiKey=" + apiKey;
        return ResponseEntity.ok(restTemplate.getForObject(url, String.class));
    }
    
   

    //CREATE
    @PostMapping
    public ResponseEntity<SavedEntity> save(@RequestBody SavedEntity recipe) {
        return ResponseEntity.ok(service.save(recipe));
    }

    //READ all
    @GetMapping
    public ResponseEntity<List<SavedEntity>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    //READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<SavedEntity> getOne(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SavedEntity> update(@PathVariable Long id, @RequestBody SavedEntity updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
