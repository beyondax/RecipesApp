package com.example.recipesapp.requests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.recipesapp.util.Constants.BASE_URL;

public class ServiceGenerator {

    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit mRetrofit = mBuilder.build();

    private static RecipeApi mRecipeAPI = mRetrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi() {
        return mRecipeAPI;
    }


}
