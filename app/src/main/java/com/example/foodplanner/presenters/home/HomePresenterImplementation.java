package com.example.foodplanner.presenters.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.MealResponse;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.home.HomeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
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
    DatabaseReference myRef;
    FirebaseDatabase database;
    SharedPreferences sharedPreferences;


    private final CompositeDisposable compositeDisposable;

    public HomePresenterImplementation(HomeView homeView, Repository repository, Context context) {
        this.homeView = homeView;
        this.repository = repository;
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
        this.firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

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
        clearApplicationCache();

        repository.clearAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d("Logout", "Local database cleared"),
                        throwable -> Log.e("Logout", "Failed to clear local database", throwable));
        homeView.onLogoutSuccess();
    }

    @Override
    public void getDataFromFirebase() {
     repository.fetchDataFromFirebase();
    }

    private void clearApplicationCache() {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception e) {
            Log.e("CACHE_CLEAR", "Failed to clear cache", e);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }






}
