package com.example.foodplanner.network;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.CountryResponse;
import com.example.foodplanner.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodPlannerService {
    @GET("random.php")
    Call<MealResponse> getMeals();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?c=list")
    Call<CountryResponse> getCountries();
}
