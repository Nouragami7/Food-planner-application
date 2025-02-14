package com.example.foodplanner.views.ui.search;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Ingredient;

import java.util.ArrayList;

public interface TheSearchView {
    void ShowIngredients(ArrayList<Ingredient> ingredients);
    void showCategories(ArrayList<Category> categories);
    void showCountries(ArrayList<Country> countries);
    void showError(String errorMsg);
}
