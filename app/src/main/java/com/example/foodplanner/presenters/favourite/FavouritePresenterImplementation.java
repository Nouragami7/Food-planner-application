package com.example.foodplanner.presenters.favourite;

import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.favourite.FavouriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenterImplementation implements FavouritePresenter {
    private final FavouriteView favouriteView;
    private final Repository repository;

    public FavouritePresenterImplementation(FavouriteView favouriteView, Repository repository) {
        this.favouriteView = favouriteView;
        this.repository = repository;
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
}
