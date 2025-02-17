package com.example.foodplanner.views.ui.home;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.interfacies.LoadingView;

import java.util.ArrayList;

public interface HomeView extends LoadingView {
     void showMeals(ArrayList<Meal> meals);
     void showCategories(ArrayList<Category> categories);
     void showCountries(ArrayList<Country> countries);
     void showError(String errorMsg);
     void onLogoutSuccess();


}
