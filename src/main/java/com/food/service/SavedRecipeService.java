package com.food.service;

import org.springframework.stereotype.Service;

import com.food.Repository.SavedRecipeRepository;
import com.food.entity.SavedEntity;
import com.food.entity.UserEntity;
import com.food.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class SavedRecipeService {

    private final SavedRecipeRepository repo;

    public SavedRecipeService(SavedRecipeRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public SavedEntity save(SavedEntity recipe) {
        return repo.save(recipe);
    }

    public boolean existsByRecipeId(String recipeId) {
        return repo.existsByRecipeId(recipeId);
    }

    // READ
    public List<SavedEntity> getAll() {
        return repo.findAll();
    }

    public Optional<SavedEntity> getById(Long id) {
        return repo.findById(id);
    }

    // UPDATE
    public SavedEntity update(Long id, SavedEntity updated) {
        SavedEntity existing = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("SavedEntity not found for id: " + id));
        existing.setTitle(updated.getTitle());
        existing.setImageUrl(updated.getImageUrl());
        existing.setReadyInMinutes(updated.getReadyInMinutes());
        return repo.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Get saved recipes by user
    public List<SavedEntity> findByUser(UserEntity user) {
        return repo.findByUser(user);
    }

    // Check if recipe is saved by a user
    public boolean existsByRecipeIdAndUser(String recipeId, UserEntity user) {
        return repo.existsByRecipeIdAndUser(recipeId, user);
    }
}
