package com.example.recipesapp.adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.recipesapp.models.Recipe;

import java.util.List;

public class RecipeDiffCallback extends DiffUtil.Callback {

    List<Recipe> oldRecipes;
    List<Recipe> newRecipes;

    public RecipeDiffCallback(List<Recipe> oldRecipes, List<Recipe> newRecipes) {
        this.oldRecipes = oldRecipes;
        this.newRecipes = newRecipes;
    }

    @Override
    public int getOldListSize() {
        if (oldRecipes != null) {
            return oldRecipes.size();
        }
        return 0;
    }

    @Override
    public int getNewListSize() {
        if (newRecipes != null) {
            return newRecipes.size();

        }
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return oldRecipes.get(oldItemPosition).getRecipeId() == newRecipes.get(newItemPosition).getRecipeId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return oldRecipes.get(oldItemPosition).getTitle().equals(newRecipes.get(newItemPosition).getTitle()) &&
                oldRecipes.get(oldItemPosition).getImageUrl().equals(newRecipes.get(newItemPosition).getImageUrl()) &&
                oldRecipes.get(oldItemPosition).getPublisher().equals(newRecipes.get(newItemPosition).getPublisher());
//                &&
//                oldRecipes.get(oldItemPosition).getSocialRank().equals(newRecipes.get(newItemPosition).getSocialRank());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
