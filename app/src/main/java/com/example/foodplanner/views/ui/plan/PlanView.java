package com.example.foodplanner.views.ui.plan;

import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

public interface PlanView {
    void showDataSuccess(List<MealStorage> mealStorages);
    void showError(String message);
}
