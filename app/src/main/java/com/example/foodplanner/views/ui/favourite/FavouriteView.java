package com.example.foodplanner.views.ui.favourite;

import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

public interface FavouriteView {
    void showDataSuccess(List<MealStorage> mealStorage);
    void showSuccessMessage(MealStorage mealStorage);
    void showError(String message);

}
