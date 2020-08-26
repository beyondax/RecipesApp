package com.example.recipesapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recipesapp.models.Recipe;
import com.example.recipesapp.viewmodels.RecipeViewModel;

import java.util.List;

public class RecipeActivity extends BaseActivity {

    private RecipeViewModel mRecipeViewModel;


    private ImageView mRecipeImage;
    private TextView mRecipeTitle, mRecipeRank;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;
    private TextView mSourceUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipeRank = findViewById(R.id.recipe_social_score);
        mLinearLayout = findViewById(R.id.ingredients_container);
        mScrollView = findViewById(R.id.parent);
        mSourceUrl = findViewById(R.id.source_url);

        mRecipeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RecipeViewModel();
            }
        }).get(RecipeViewModel.class);


        mSourceUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeUrl();
            }
        });

        showProgressBar(true);
        subscribeObservers();
        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d("EXTRA", "getIncomingIntent: " + recipe.getTitle());
            mRecipeViewModel.searchRecipeById(recipe.getRecipeId());
        }
    }

    private void subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    if (recipe.getRecipeId().equals(mRecipeViewModel.getRecipeId())) {
                        setRecipeProperties(recipe);
                        mRecipeViewModel.setDidRetrieveRecipe(true);
                    }
                }
            }
        });

        mRecipeViewModel.isRecipeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mRecipeViewModel.isDidRetrieveRecipe()) {
                    Log.d("Request", "onChanged: timed out");
                    displayErrorScreen("Error retrieving data...");
                }
            }
        });


    }

    private void displayErrorScreen(String errorMessage) {
        mRecipeTitle.setText(errorMessage);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(mRecipeImage);

        showParent();
        showProgressBar(false);
    }

    private void setRecipeProperties(Recipe recipe) {
        if (recipe != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);


            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImageUrl())
                    .into(mRecipeImage);

            mRecipeTitle.setText(recipe.getTitle());
            mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocialRank())));

            mLinearLayout.removeAllViews();


            mSourceUrl.setText(recipe.getSourceUrl());
            mSourceUrl.setTextSize(15);

            showParent();
            showProgressBar(false);
        }
    }

    private void openRecipeUrl() {
        if (mSourceUrl.getText() != null) {
            String url = mSourceUrl.getText().toString();
            PackageManager packageManager = getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if (isIntentSafe) {
                startActivity(intent);
            }
        }
    }

    private void showParent() {
        mScrollView.setVisibility(View.VISIBLE);
    }
}
