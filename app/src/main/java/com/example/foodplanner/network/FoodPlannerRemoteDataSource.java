package com.example.foodplanner.network;

import android.util.Log;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.DTOS.MealCountryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodPlannerRemoteDataSource {
    public static final String TAG = "MEAL";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static FoodPlannerService foodPlannerService;
    private static FoodPlannerRemoteDataSource client = null;

    private FoodPlannerRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        foodPlannerService = retrofit.create(FoodPlannerService.class);
    }

    public static FoodPlannerRemoteDataSource getInstance() {
        if (client == null) {
            client = new FoodPlannerRemoteDataSource();
        }
        return client;
    }

    public Single<MealResponse> getMeals() {
        return foodPlannerService.getMeals();
    }

    public Single<CategoryResponse> getCategories() {
        return foodPlannerService.getCategories();
    }

    public Single<CountryResponse> getCountries() {
        return foodPlannerService.getCountries();
    }

    public Single<MealCategoryResponse> getMealsByCategory(String category) {
        return foodPlannerService.getMealsByCategory(category);
    }
    public Single<MealCountryResponse> getMealsByCountry(String country) {
        return foodPlannerService.getMealsByCountry(country);
    }

}









