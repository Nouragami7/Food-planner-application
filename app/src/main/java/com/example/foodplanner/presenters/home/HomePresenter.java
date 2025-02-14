package com.example.foodplanner.presenters.home;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter {
   public void getMeals();
   public void getCategories();
   public void getCountries();


}
