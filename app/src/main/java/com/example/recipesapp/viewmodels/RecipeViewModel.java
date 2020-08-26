package com.example.recipesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mDidRetrieveRecipe;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetrieveRecipe = false;
    }

    public boolean isDidRetrieveRecipe() {
        return mDidRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean didRetrieveRecipe) {
        mDidRetrieveRecipe = didRetrieveRecipe;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeRepository.getRecipe();
    }
    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return mRecipeRepository.isRecipeRequestTimedOut();
    }

    public String getRecipeId() {
        return mRecipeId;
    }


    public void searchRecipeById(String recipeId) {
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }
}
