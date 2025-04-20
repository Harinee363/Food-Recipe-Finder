package com.food.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.entity.SavedEntity;
import com.food.entity.UserEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavedRecipeRepository extends JpaRepository<SavedEntity, Long> {
    Optional<SavedEntity> findByRecipeId(String recipeId);
    boolean existsByRecipeId(String recipeId);
    List<SavedEntity> findByUser(UserEntity user);  // Corrected this method
    boolean existsByRecipeIdAndUser(String recipeId, UserEntity user);
}
