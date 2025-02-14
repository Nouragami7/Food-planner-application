package com.example.foodplanner.views.ui.meal_details;

import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.views.ui.loadingView.LoadingView;

public interface MealDetailsView  {
    public void showMealDetailsById(Meal meal);

    void showError(String message);
}
