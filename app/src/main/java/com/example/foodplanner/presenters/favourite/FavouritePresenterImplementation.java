package com.example.foodplanner.presenters.favourite;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.favourite.FavouriteView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenterImplementation implements FavouritePresenter {
    private final FavouriteView favouriteView;
    private final Repository repository;

    SharedPreferences sharedPreferences;

    DatabaseReference myRef;
    FirebaseDatabase database;

    public FavouritePresenterImplementation(FavouriteView favouriteView, Repository repository, Context context) {
        this.favouriteView = favouriteView;
        this.repository = repository;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Meals");
    }

    @Override
    public void getAllFavoriteMeals() {
        repository.getAllFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealStorages -> {
                            if (mealStorages != null) {
                                favouriteView.showDataSuccess(mealStorages);
                            } else {
                                favouriteView.showError("No meals found");
                            }

                        });

    }
    @Override
    public void deleteMealFromFavourite(MealStorage mealStorage) {
        repository.deleteMeal(mealStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            favouriteView.showSuccessMessage(mealStorage);
                        }
                        , throwable -> {
                            favouriteView.showError(throwable.getMessage());
                        }

                );

    }

    @Override
    public void deleteData(MealStorage mealStorage) {
        String userId = sharedPreferences.getString("userId", null);
        if (userId != null) {
            myRef.child("Users")
                    .child(userId)
                    .child(mealStorage.getMealId())
                    .child(mealStorage.getDate())
                    .removeValue();
        }

    }
}
