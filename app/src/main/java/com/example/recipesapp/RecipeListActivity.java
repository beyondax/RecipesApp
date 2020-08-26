package com.example.recipesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipesapp.adapters.OnRecipeListener;
import com.example.recipesapp.adapters.RecipeRecyclerAdapter;
import com.example.recipesapp.models.Recipe;

import com.example.recipesapp.util.Testing;
import com.example.recipesapp.util.VerticalSpacingItemDecorator;
import com.example.recipesapp.viewmodels.RecipeListViewModel;


import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    public static final String TAG = RecipeListActivity.class.getSimpleName();

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);

        mRecipeListViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RecipeListViewModel();
            }
        }).get(RecipeListViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));

        initRecyclerView();
        subscribeObservers();
        initSearchView();

        if (!mRecipeListViewModel.isViewingRecipes()) {
            displaySearchCategories();
        }


    }

    private void initSearchView() {

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query, 1);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    mRecipeListViewModel.searchNextPage();
                }
            }
        });
    }

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    if (mRecipeListViewModel.isViewingRecipes()) {

                        Testing.printRecipes(recipes, "test");
                        mRecipeListViewModel.setPerformingQuery(false);
                        mAdapter.setRecipes(recipes);
                    }
                }
            }
        });

        mRecipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
               if(aBoolean) {
                   Log.d(TAG, "onChanged: the query is exhausted!");
                   mAdapter.setQueryExhausted();
               }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mRecipeListViewModel.onBackPressed()) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }


    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", mAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category, 1);
        mSearchView.clearFocus();
    }

    private void displaySearchCategories() {
        mRecipeListViewModel.setViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories) {
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
