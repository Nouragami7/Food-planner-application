package com.example.foodplanner.network;

import android.util.Log;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.CountryResponse;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealResponse;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String TAG = "MEAL";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static FoodPlannerService foodPlannerService;

    private static Retrofit retrofit;

    private static RetrofitClient client = null;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        foodPlannerService = retrofit.create(FoodPlannerService.class);
    }

    public static RetrofitClient getInstance() {
        if (client == null) {
            client = new RetrofitClient();
        }
        return client;
    }

    public void makeNetworkCall(NetworkCallback networkCallback, int numofCalls) {
        ArrayList<Meal> allMeals = new ArrayList<>();
        AtomicInteger completedRequests = new AtomicInteger(0);
        for (int i = 1; i <= numofCalls; i++) {
            foodPlannerService.getMeals().enqueue(new Callback<MealResponse>() {
                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ArrayList<Meal> meals = response.body().getMeals();
                        if (meals != null && !meals.isEmpty()) {
                            allMeals.addAll(meals);
                            Log.i(TAG, "Meal added: " + meals.get(0).getStrMeal());
                        }
                    }
                    if (!allMeals.isEmpty()) {
                        networkCallback.onSuccessResult(allMeals);
                    } else {
                        networkCallback.onFailureResult("Failed to fetch meals");
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                    networkCallback.onFailureResult(t.getMessage());
                    Log.i(TAG, "onFailure: " + t.getMessage());

                }
            });
        }
    }

    public void makeNetworkCallCategory(NetworkCallback networkCallback) {
        foodPlannerService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                        networkCallback.onSuccessResultCategory(response.body().getCategories());
                        Log.i(TAG, "Meal added: " + response.body().getCategories().get(0).getCategoryName());
                    } else {
                        networkCallback.onFailureResult("Failed to fetch categories");
                        Log.i(TAG, "onFailure: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
                Log.i(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    public void makeNetworkCallArea(NetworkCallback networkCallback, String filter) {
     foodPlannerService.getCountries("list").enqueue(new Callback<CountryResponse>() {
         @Override
         public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
             if (response.isSuccessful() && response.body() != null) {
                 networkCallback.onSuccesResultArea(response.body().getCountries());
             }else {
                 networkCallback.onFailureResult("Failed to fetch categories");
             }

         }

         @Override
         public void onFailure(Call<CountryResponse> call, Throwable t) {
             networkCallback.onFailureResult(t.getMessage());
         }
     });
    }
}
