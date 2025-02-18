package com.example.foodplanner.views.ui.favourite;

import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

public interface FavouriteView {
    void showDataSuccess(List<MealStorage> mealStorage);
    void displaySuccess(MealStorage mealStorage);
    void showError(String message);

}
