package com.example.foodplanner.presenters.mealfiltering;

import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealCountryResponse;
import com.example.foodplanner.models.DTOS.MealIngredientResponse;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.views.ui.meal_filtering.MealFilteringView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFilteringPresenterImplementation implements MealFilteringPresenter{
    private final MealFilteringView mealFilteringView;
    private final Repository repository;

    private final CompositeDisposable compositeDisposable;


    public MealFilteringPresenterImplementation(MealFilteringView mealFilteringView, Repository repository) {
        this.mealFilteringView = mealFilteringView;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getMealsByCategory(String category) {
        mealFilteringView.showLoading();
        Disposable disposable = repository.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .map(MealCategoryResponse::getMealsFromCategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealCategoryResponse -> {
                    mealFilteringView.hideLoading();
                    mealFilteringView.showMealsByCategory(mealCategoryResponse);
                }, throwable -> mealFilteringView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByCountry(String area) {
        mealFilteringView.showLoading();
        Disposable disposable = repository.getMealsByCountry(area)
                .subscribeOn(Schedulers.io())
                .map(MealCountryResponse::getMealsFromCountry)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealCategoryResponse -> {
                    mealFilteringView.hideLoading();
                    mealFilteringView.showMealsByCountry(mealCategoryResponse);
                }, throwable -> mealFilteringView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        mealFilteringView.showLoading();
        Disposable disposable = repository.getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .map(MealIngredientResponse::getMealsFromIngredient)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealIngredientResponse -> {
                    mealFilteringView.hideLoading();
                    mealFilteringView.showMealsByIngredient(mealIngredientResponse);
                }, throwable -> mealFilteringView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }


}
