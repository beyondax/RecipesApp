package com.example.recipesapp.requests.responses;

import com.example.recipesapp.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {


    @SerializedName("count")
    @Expose
    private int mCount;

    @SerializedName("recipes")
    @Expose
    private List<Recipe> mRecipes;

    public int getCount() {
        return mCount;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "mCount=" + mCount +
                ", mRecipes=" + mRecipes +
                '}';
    }
}
