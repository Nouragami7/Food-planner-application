package com.example.foodplanner.network;

import android.util.Log;

import com.example.foodplanner.model.CategoryResponse;
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
    private static RetrofitClient client = null;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
                            synchronized (allMeals) {
                                allMeals.addAll(meals);
                            }
                        }
                    }
                    if (completedRequests.incrementAndGet() == numofCalls) {
                        if (!allMeals.isEmpty()) {
                            networkCallback.onSuccessResult(allMeals);
                        } else {
                            networkCallback.onFailureResult("Failed to fetch meals");
                        }
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                    if (completedRequests.incrementAndGet() == numofCalls) {
                        networkCallback.onFailureResult(t.getMessage());
                    }
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void makeNetworkCallCategory(NetworkCallback networkCallback) {
        foodPlannerService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getCategories() != null) {
                    networkCallback.onSuccessResultCategory(response.body().getCategories());
                    if (!response.body().getCategories().isEmpty()) {
                        Log.i(TAG, "Category added: " + response.body().getCategories().get(0).getCategoryName());
                    }
                } else {
                    networkCallback.onFailureResult("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void makeNetworkCallArea(NetworkCallback networkCallback, String area) {
        foodPlannerService.getCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getCountries() != null) {
                    networkCallback.onSuccesResultArea(response.body().getCountries());
                } else {
                    networkCallback.onFailureResult("Failed to fetch countries");
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());
            }
        });
    }
}
