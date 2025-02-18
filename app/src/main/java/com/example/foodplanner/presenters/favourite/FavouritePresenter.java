package com.example.foodplanner.presenters.favourite;

import com.example.foodplanner.models.database.MealStorage;

public interface FavouritePresenter {
    void getAllFavoriteMeals();
    void deleteMealFromFavourite(MealStorage mealStorage);
    void deleteData(MealStorage mealStorage);


}
