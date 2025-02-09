package com.example.foodplanner.network;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;

public interface NetworkCallback {
    public void onSuccessResult(ArrayList<Meal> meals);
    public void onSuccessResultCategory(ArrayList<Category> Categories);

   public void onSuccesResultArea(ArrayList<Country> meals);

    public void onFailureResult(String errorMsg);
}
