package com.example.foodplanner.presenters.mealdetails;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.meal_details.MealDetailsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImplementation implements MealDetailsPresenter {
    private final MealDetailsView mealDetailsView;
    private final Repository repository;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;

    public MealDetailsPresenterImplementation(MealDetailsView mealDetailsView, Repository repository, Context context) {
        this.mealDetailsView = mealDetailsView;
        this.repository = repository;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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

    @Override
    public void addToFavourite(MealStorage mealStorage) {
        repository.insertMeal(mealStorage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mealDetailsView.showSuccessMessage("Meal added to favourites"),
                        throwable -> mealDetailsView.showError(throwable.getMessage()));


    }

    @Override
    public void addToPlan(MealStorage mealStorage) {
        repository.insertMeal(mealStorage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mealDetailsView.showSuccessMessage("Meal added to plan"),
                        throwable -> mealDetailsView.showError(throwable.getMessage()));
    }

    @Override
    public void deleteMealFromFavourite(MealStorage mealStorage) {
        repository.deleteMeal(mealStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            mealDetailsView.showSuccessMessage("Sccessfully deleted");
                        }
                        , throwable -> {
                            mealDetailsView.showError(throwable.getMessage());
                        });
    }

    @Override
    public void sendData(MealStorage mealStorage) {
        String userId = sharedPreferences.getString("userId", null);
        myRef.child("Users")
                .child(userId)
                .child(mealStorage.getMealId())
                .child(mealStorage.getDate())
                .setValue(mealStorage);
    }
}



