package com.example.foodplanner.presenters.mealdetails;

import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.loadingView.LoadingView;

import io.reactivex.rxjava3.core.Completable;

public interface MealDetailsPresenter {
    public void getMealDetailsById(int id);
    public void addToFavourite(MealStorage mealStorage);

}
