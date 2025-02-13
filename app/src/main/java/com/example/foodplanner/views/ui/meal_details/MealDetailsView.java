package com.example.foodplanner.views.ui.meal_details;

import com.example.foodplanner.models.DTOS.Meal;

public interface MealDetailsView {
    void showMealDetails(Meal meal);
    void showError(String errorMsg);
    void showLoading();
    void hideLoading();
    void loadYouTubeVideo(String iframe);

}
