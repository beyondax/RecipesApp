package com.example.recipesapp.viewmodels;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private boolean mIsViewingRecipes;
    private boolean mIsPerformingQuery;

    public LiveData<Boolean> isQueryExhausted() {
        return mRecipeRepository.isQueryExhausted();
    }

    public boolean isViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        mIsViewingRecipes = viewingRecipes;
    }

    private RecipeRepository mRecipeRepository;


    public RecipeListViewModel() {
        mIsViewingRecipes = false;
        mRecipeRepository = RecipeRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber) {
        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipesApi(query, pageNumber);
        mIsPerformingQuery = true;
    }

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setPerformingQuery(boolean performingQuery) {
        mIsPerformingQuery = performingQuery;
    }

    public boolean onBackPressed() {
       if(isPerformingQuery()) {
           mRecipeRepository.cancelRequest();
           mIsPerformingQuery = false;
       }

       if(mIsViewingRecipes) {
            mIsViewingRecipes = false;
            return false;
        }
        return true;
    }

    public void searchNextPage() {
        if (!mIsPerformingQuery
                && mIsViewingRecipes
        && !isQueryExhausted().getValue()) {
            mRecipeRepository.nextPage();

        }
    }
}

