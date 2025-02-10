package com.example.foodplanner.network;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.CountryResponse;
import com.example.foodplanner.model.MealResponse;
import com.example.foodplanner.model.MealSpecification;
import com.example.foodplanner.model.MealSpecificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodPlannerService {
    @GET("random.php")
    Call<MealResponse> getMeals();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?c=list")
    Call<CountryResponse> getCountries();

    @GET("filter.php")
    Call<MealSpecificationResponse> getMealsByCategory(@Query("c") String category);
}
