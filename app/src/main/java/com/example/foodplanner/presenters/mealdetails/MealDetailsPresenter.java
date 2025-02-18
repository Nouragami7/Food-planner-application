package com.example.foodplanner.presenters.mealdetails;

import com.example.foodplanner.models.database.MealStorage;

public interface MealDetailsPresenter {
     void getMealDetailsById(int id);
     void addToFavourite(MealStorage mealStorage);
     void addToPlan(MealStorage mealStorage);
     void deleteMealFromFavourite(MealStorage mealStorage);
     void sendData(MealStorage mealStorage);

}
