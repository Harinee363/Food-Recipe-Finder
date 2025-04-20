package com.food.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpoonacularResponse {

    private List<Recipe> results;

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    public static class Recipe {
        private int id;
        private String title;
        private String image;
        private int readyInMinutes;

        @JsonProperty("extendedIngredients")
        private List<Ingredient> ingredients;

        public int getReadyInMinutes() {
            return readyInMinutes;
        }

        public void setReadyInMinutes(int readyInMinutes) {
            this.readyInMinutes = readyInMinutes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public static class Ingredient {
            private String original;

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
            }
        }
    }
}
