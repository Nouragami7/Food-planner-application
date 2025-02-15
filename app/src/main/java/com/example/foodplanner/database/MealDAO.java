package com.example.foodplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.models.database.MealStorage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meals_table WHERE isFavourite = 1")
    Single<List<MealStorage>> getAllFavouriteMeals();

    @Query("SELECT * FROM meals_table WHERE isPlanned = 1")
    Single<List<MealStorage>> getAllPlannedMeals();

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   Completable insertMeal(MealStorage mealStorage);

   @Delete
   Completable deleteMeal(MealStorage mealStorage);

}
