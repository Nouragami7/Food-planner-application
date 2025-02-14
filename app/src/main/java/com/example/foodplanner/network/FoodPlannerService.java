package com.example.foodplanner.network;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.IngredientResponse;
import com.example.foodplanner.models.DTOS.MealCountryResponse;
import com.example.foodplanner.models.DTOS.MealIngredientResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealSpecification;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodPlannerService {
    @GET("random.php")
    Single<MealResponse> getMeals();

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Single<CountryResponse> getCountries();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("filter.php")
    Single<MealCategoryResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealCountryResponse> getMealsByCountry(@Query("a") String country);

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") int mealId);

    @GET("filter.php")
    Single<MealIngredientResponse> getMealsByIngredient(@Query("i") String ingredient);
}
