package com.example.foodplanner.presenters.mealfiltering;

import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealCountryResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealFilteringPresenter {
   void getMealsByCategory(String category);
    void getMealsByCountry(String area);


}
