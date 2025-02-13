package com.example.foodplanner.presenters.home;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.DTOS.MealCategoryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.views.ui.home.HomeView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImplementation implements HomePresenter {

    private final HomeView homeView;
    private final Repository repository;

   private final CompositeDisposable compositeDisposable;
    public HomePresenterImplementation(HomeView homeView, Repository repository) {
        this.homeView = homeView;
        this.repository=repository;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public Single<MealResponse> getMeals() {
        Disposable disposable = repository.getMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    ArrayList<Meal> meals = mealResponse.getMeals();
                    homeView.showMeals(meals);
                }, throwable -> homeView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);
        return repository.getMeals();
    }

    @Override
    public Single<CategoryResponse> getCategories() {
        Disposable disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    ArrayList<Category> categories = categoryResponse.getCategories();
                    homeView.showCategories(categories);
                }, throwable -> homeView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);
        return repository.getCategories();
    }

    @Override
    public Single<CountryResponse> getCountries() {
        Disposable disposable = repository.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponse -> {
                    ArrayList<Country> countries = countryResponse.getCountries();
                    homeView.showCountries(countries);
                }, throwable -> homeView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
        return repository.getCountries();
    }

}
