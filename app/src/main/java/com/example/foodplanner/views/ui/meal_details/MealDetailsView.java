package com.example.foodplanner.views.ui.meal_details;

import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.database.MealStorage;

public interface MealDetailsView  {
    public void showMealDetailsById(Meal meal);
    public void showSuccessMessage(String message);
    void deleteMealFromFavourite(MealStorage mealStorage);

    void showError(String message);
}
