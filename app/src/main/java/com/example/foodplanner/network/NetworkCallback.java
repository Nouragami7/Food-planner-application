package com.example.foodplanner.network;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.DTOS.MealSpecification;

import java.util.ArrayList;

public interface NetworkCallback {
    public void onSuccessResult(ArrayList<Meal> meals);
    public void onSuccessResultCategory(ArrayList<Category> Categories);

   public void onSuccesResultArea(ArrayList<Country> meals);

   public void onSuccessResultMealSpecification(ArrayList<MealSpecification> meals);

    public void onFailureResult(String errorMsg);
}
