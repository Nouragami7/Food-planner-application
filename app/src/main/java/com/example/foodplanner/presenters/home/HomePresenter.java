package com.example.foodplanner.presenters.home;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter {
   public Single<MealResponse> getMeals();
   public Single<CategoryResponse> getCategories();
   public Single<CountryResponse> getCountries();


}
