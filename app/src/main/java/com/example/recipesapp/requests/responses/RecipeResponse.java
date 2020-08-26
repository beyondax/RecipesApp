package com.example.recipesapp.requests.responses;

import com.example.recipesapp.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose
    private Recipe mRecipe;

    public Recipe getRecipe() {
        return mRecipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "mRecipe=" + mRecipe +
                '}';
    }
}
