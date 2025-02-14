package com.example.foodplanner.views.ui.home;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.views.ui.loadingView.LoadingView;

import java.util.ArrayList;

public interface HomeView extends LoadingView {
    public void showMeals(ArrayList<Meal> meals);
    public void showCategories(ArrayList<Category> categories);
    public void showCountries(ArrayList<Country> countries);
    public void showError(String errorMsg);

}
