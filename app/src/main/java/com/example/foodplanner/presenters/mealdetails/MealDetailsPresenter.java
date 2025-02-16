package com.example.foodplanner.presenters.mealdetails;

import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.loadingView.LoadingView;

import io.reactivex.rxjava3.core.Completable;

public interface MealDetailsPresenter {
     void getMealDetailsById(int id);
     void addToFavourite(MealStorage mealStorage);
     void addToPlan(MealStorage mealStorage);
     void sendData(MealStorage mealStorage);

}
