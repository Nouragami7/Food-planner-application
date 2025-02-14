package com.example.foodplanner.presenters.mealdetails;

import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.views.ui.meal_details.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImplementation implements MealDetailsPresenter {
    private final MealDetailsView mealDetailsView;
    private final Repository repository;

    public MealDetailsPresenterImplementation(MealDetailsView mealDetailsView, Repository repository) {
        this.mealDetailsView = mealDetailsView;
        this.repository = repository;
    }

    @Override
    public void getMealDetailsById(int id) {
        Disposable disposable = repository.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    mealDetailsView.showMealDetailsById(mealResponse.getMeals().get(0));
                }, throwable -> mealDetailsView.showError(throwable.getMessage()));
    }
}



