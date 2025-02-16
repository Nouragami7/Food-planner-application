package com.example.foodplanner.presenters.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.views.ui.home.HomeView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImplementation implements HomePresenter {

    private final HomeView homeView;
    private final Repository repository;
    FirebaseAuth firebaseAuth;
    Context context;

   private final CompositeDisposable compositeDisposable;
    public HomePresenterImplementation(HomeView homeView, Repository repository, Context context) {
        this.homeView = homeView;
        this.repository=repository;
        this.context=context;
        this.compositeDisposable = new CompositeDisposable();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void getMeals() {
        homeView.showLoading();

        Disposable disposable = Observable.concat(
                        repository.getMeals().toObservable(),
                        repository.getMeals().toObservable(),
                        repository.getMeals().toObservable(),
                        repository.getMeals().toObservable()
                )
                .subscribeOn(Schedulers.io())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .toList()
                .map(ArrayList::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    homeView.hideLoading();
                    homeView.showMeals(meals);
                }, throwable -> {
                    homeView.hideLoading();
                    homeView.showError(throwable.getMessage());
                });

        compositeDisposable.add(disposable);
    }
    @Override
    public void getCategories() {
        homeView.showLoading();
        Disposable disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .map(CategoryResponse::getCategories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    homeView.hideLoading();
                    homeView.showCategories(categoryResponse);
                }, throwable -> homeView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);
    }

    @Override
    public void getCountries() {
        Disposable disposable = repository.getCountries()
                .subscribeOn(Schedulers.io())
                .map(CountryResponse::getCountries)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countries -> {
                    homeView.hideLoading();
                    homeView.showCountries(countries);
                }, throwable -> homeView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        homeView.onLogoutSuccess();
    }

}
