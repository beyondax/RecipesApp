package com.example.recipesapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;

    private RecipeApiClient mRecipeApiClient;

    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

    private String mQuery;
    private int mPageNumber;




    public static RecipeRepository getInstance() {

        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;

    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();

    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeApiClient.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return mRecipeApiClient.getRecipeRequestTimedOut();
    }


    public void searchRecipesApi(String query, int pageNumber) {

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mRecipeApiClient.searchRecipesApi(query, pageNumber);
        mIsQueryExhausted.setValue(false);
    }

    public void cancelRequest() {
        mRecipeApiClient.cancelRequest();
    }

    public void nextPage() {
        searchRecipesApi(mQuery, mPageNumber + 1);
    }

    public void searchRecipeById(String id) {
        mRecipeApiClient.searchRecipeById(id);
    }

    private void initMediators() {
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null) {
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }
                else {
                    doneQuery(null);
                }
            }
        });
    }

    public void doneQuery(List<Recipe> list) {

        if (list != null) {
            if (list.size() % 30 != 0) {
                mIsQueryExhausted.setValue(true);
            }
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }
}


