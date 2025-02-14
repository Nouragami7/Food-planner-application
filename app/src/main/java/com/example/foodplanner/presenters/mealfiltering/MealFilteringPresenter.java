package com.example.foodplanner.presenters.mealfiltering;

public interface MealFilteringPresenter {
   void getMealsByCategory(String category);
    void getMealsByCountry(String area);
    void getMealsByIngredient(String ingredient);


}
