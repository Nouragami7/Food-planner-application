package com.example.foodplanner.database;


import android.content.Context;

import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FoodPlannerLocalDataSource {
    private static FoodPlannerLocalDataSource foodPlannerLocalDataSource;
    private MealDAO mealDAO;
    private ApplicationDatabase database;
    private FoodPlannerLocalDataSource(Context context) {
        database = ApplicationDatabase.getInstance(context);
        this.database = database;
        mealDAO = database.mealDAO();
    }
    public static FoodPlannerLocalDataSource getInstance(Context context) {
        if (foodPlannerLocalDataSource == null) {
            foodPlannerLocalDataSource = new FoodPlannerLocalDataSource(context);
        }
        return foodPlannerLocalDataSource;
    }
    public Single<List<MealStorage>> getAllFavouriteMeals() {
        return mealDAO.getAllFavouriteMeals();
    }
    public Single<List<MealStorage>> getAllPlannedMeals() {
        return mealDAO.getAllPlannedMeals();
    }
    public Completable insertMeal(MealStorage mealStorage) {
        return mealDAO.insertMeal(mealStorage);
    }

    public Completable deleteMeal(MealStorage mealStorage) {
        return mealDAO.deleteMeal(mealStorage);
    }

}
