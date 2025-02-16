package com.example.foodplanner.models.Repository;

import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.IngredientResponse;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealCountryResponse;
import com.example.foodplanner.models.DTOS.MealIngredientResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    private static Repository repository = null;
    private FoodPlannerRemoteDataSource remoteDataSource;
    private FoodPlannerLocalDataSource localDataSource;

    private Repository(FoodPlannerRemoteDataSource remoteDataSource, FoodPlannerLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static Repository getInstance(FoodPlannerRemoteDataSource remoteDataSource, FoodPlannerLocalDataSource localDataSource) {
        if (repository == null) {
            repository = new Repository(remoteDataSource,localDataSource);
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
    public  Single<IngredientResponse> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    public Single<MealCategoryResponse> getMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category);
    }
    public Single<MealCountryResponse> getMealsByCountry(String area) {
        return remoteDataSource.getMealsByCountry(area);
    }
    public Single<MealIngredientResponse> getMealsByIngredient(String ingredient) {
        return remoteDataSource.getMealsByIngredient(ingredient);
    }

    public Single<MealResponse> getMealById(int mealId) {
        return remoteDataSource.getMealById(mealId);
    }

    public Completable insertMeal(MealStorage mealStorage) {
        return localDataSource.insertMeal(mealStorage);
    }

    public Single<List<MealStorage>> getAllFavouriteMeals() {
        return localDataSource.getAllFavouriteMeals();
    }
    public Single<List<MealStorage>> getAllMealsFromPlan() {
        return localDataSource.getAllPlannedMeals();
    }

    public Completable deleteMeal(MealStorage mealStorage) {
        return localDataSource.deleteMeal(mealStorage);
    }
}
