package com.example.foodplanner.models.Repository;

import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealCountryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.network.FoodPlannerService;
import com.example.foodplanner.network.NetworkCallback;

import io.reactivex.rxjava3.core.Single;

public class Repository {

    private static Repository repository = null;
    private FoodPlannerRemoteDataSource remoteDataSource;

    private Repository(FoodPlannerRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(FoodPlannerRemoteDataSource remoteDataSource) {
        if (repository == null) {
            repository = new Repository(remoteDataSource);
        }
        return repository;
    }

    public Single<MealResponse> getMeals() {
        return remoteDataSource.getMeals();
    }

    public Single<CategoryResponse> getCategories() {
        return remoteDataSource.getCategories();
    }

    public Single<CountryResponse> getCountries() {
        return remoteDataSource.getCountries();
    }

    public Single<MealCategoryResponse> getMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category);
    }
    public Single<MealCountryResponse> getMealsByCountry(String area) {
        return remoteDataSource.getMealsByCountry(area);
    }

}
