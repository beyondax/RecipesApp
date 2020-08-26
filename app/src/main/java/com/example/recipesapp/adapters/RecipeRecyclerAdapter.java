package com.example.recipesapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.recipesapp.R;
import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int EXHAUSTED_TYPE = 4;


    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        mOnRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {
            case LOADING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list, parent, false);
                return new LoadingViewHolder(view);
            case CATEGORY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list, parent, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            case EXHAUSTED_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_exhausted, parent, false);
                return new SearchExhaustedViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list, parent, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(((RecipeViewHolder) holder).itemView)
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(position).getImageUrl())
                    .into(((RecipeViewHolder) holder).image);

            ((RecipeViewHolder) holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(mRecipes.get(position).getPublisher());
            ((RecipeViewHolder) holder).socialScore.setText(format("%.1f", mRecipes.get(position).getSocialRank()));

        } else if (itemViewType == CATEGORY_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Uri uri = Uri.parse("android.resource://com.example.recipesapp/drawable/" + mRecipes.get(position).getImageUrl());
            Glide.with(((CategoryViewHolder) holder).itemView)
                    .setDefaultRequestOptions(requestOptions)
                    .load(uri)
                    .into(((CategoryViewHolder) holder).mCategoryCircleImageView);

            ((CategoryViewHolder) holder).mCategoryTitleTextView.setText(mRecipes.get(position).getTitle());

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecipes.get(position).getSocialRank() == -1) {
            return CATEGORY_TYPE;
        } else if (mRecipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mRecipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == mRecipes.size() - 1
                && position != 0
                && !mRecipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void setQueryExhausted() {
            hideLoading();
            Recipe exhaustedRecipe = new Recipe();
            exhaustedRecipe.setTitle("EXHAUSTED...");
            mRecipes.add(exhaustedRecipe);
            notifyDataSetChanged();
    }

    private void hideLoading() {
        if (isLoading()) {
            for (Recipe recipe :
                    mRecipes) {
                if (recipe.getTitle().equals("LOADING...")){
                    mRecipes.remove(recipe);
                }

            }
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }
    }

    public void displaySearchCategories() {
        List<Recipe> categories = new ArrayList<>();

        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORY_IMAGES.length; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImageUrl(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocialRank(-1);
            categories.add(recipe);
        }

        mRecipes = categories;
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        if (mRecipes.size() > 0) {
            if (mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {

//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RecipeDiffCallback(mRecipes, recipes));
        mRecipes = recipes;
        notifyDataSetChanged();
//            diffResult.dispatchUpdatesTo(this);

    }

    public Recipe getSelectedRecipe(int position) {
        if (!mRecipes.isEmpty()) {
            return mRecipes.get(position);
        }
        return null;
    }
}
