package com.example.foodplanner.presenters.search;

import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Ingredient;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.views.ui.search.TheSearchView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImplementation implements SearchPresenter {
    private final TheSearchView searchView;
    private final Repository repository;
    private final CompositeDisposable compositeDisposable;
    public SearchPresenterImplementation(TheSearchView searchView, Repository repository) {
        this.searchView = searchView;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getIngredients() {
        Disposable disposable = repository.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientResponse -> {
                    ArrayList<Ingredient> ingredients = ingredientResponse.getMeals();
                    searchView.ShowIngredients(ingredients);
                }, throwable -> searchView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCategories() {
        Disposable disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    ArrayList<Category> categories = categoryResponse.getCategories();
                    searchView.showCategories(categories);
                }, throwable -> searchView.showError(throwable.getMessage()));

        compositeDisposable.add(disposable);

    }

    @Override
    public void getCountries() {
       Disposable disposable = repository.getCountries()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(countryResponse -> {
                   ArrayList<Country> countries = countryResponse.getCountries();
                   searchView.showCountries(countries);
               }, throwable -> searchView.showError(throwable.getMessage()));

       compositeDisposable.add(disposable);

    }

}
