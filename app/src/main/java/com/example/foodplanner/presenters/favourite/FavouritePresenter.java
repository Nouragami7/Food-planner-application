package com.example.foodplanner.presenters.favourite;

import com.example.foodplanner.models.database.MealStorage;

public interface FavouritePresenter {
   public void getAllFavoriteMeals();
   public void deleteMealFromFavourite(MealStorage mealStorage);

}
