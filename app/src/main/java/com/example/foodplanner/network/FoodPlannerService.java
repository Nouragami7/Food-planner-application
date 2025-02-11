package com.example.foodplanner.network;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.CountryResponse;
import com.example.foodplanner.model.MealResponse;
import com.example.foodplanner.model.MealCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodPlannerService {
    @GET("random.php")
    Call<MealResponse> getMeals();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountryResponse> getCountries();

    @GET("filter.php")
    Call<MealCategoryResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealCategoryResponse> getMealsByCountry(@Query("a") String country);
}
