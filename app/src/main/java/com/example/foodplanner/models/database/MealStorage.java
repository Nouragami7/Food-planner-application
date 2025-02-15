package com.example.foodplanner.models.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.utilts.Converter;

import java.util.Date;
import java.util.Stack;

@Entity(tableName = "meals_table",
        primaryKeys = {"mealId","userId","date"})
@TypeConverters(Converter.class)
public class MealStorage {
    @NonNull
    private String mealId;

    @NonNull
    String userId;

    @NonNull
    String date;

    @TypeConverters(Converter.class)
    Meal meal;

    boolean isFavourite;

    public boolean isPlanned() {
        return isPlanned;
    }

    public void setPlanned(boolean planned) {
        isPlanned = planned;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    boolean isPlanned;

    public MealStorage(boolean isPlanned, boolean isFavourite, Meal meal, @NonNull String date, @NonNull String userId, @NonNull String mealId) {
        this.isPlanned = isPlanned;
        this.isFavourite = isFavourite;
        this.meal = meal;
        this.date = date;
        this.userId = userId;
        this.mealId = mealId;
    }



}
