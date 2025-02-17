package com.example.foodplanner.views.ui.meal_details;

import com.example.foodplanner.models.DTOS.Meal;

public interface MealDetailsView  {
    public void showMealDetailsById(Meal meal);
    public void showSuccessMessage(String message);

    void showError(String message);
}
