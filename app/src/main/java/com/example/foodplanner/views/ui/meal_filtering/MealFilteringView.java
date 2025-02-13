package com.example.foodplanner.views.ui.meal_filtering;

import com.example.foodplanner.models.DTOS.MealSpecification;

import java.util.ArrayList;

public interface MealFilteringView  {
    public void showMealsByCategory(ArrayList<MealSpecification> meals);
    public void showMealsByCountry(ArrayList<MealSpecification> meals);
    public void showError(String errorMsg);

}
