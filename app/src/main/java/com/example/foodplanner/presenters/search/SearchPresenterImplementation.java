package com.example.foodplanner.presenters.search;

import com.example.foodplanner.models.DTOS.CategoryResponse;
import com.example.foodplanner.models.DTOS.CountryResponse;
import com.example.foodplanner.models.DTOS.IngredientResponse;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.views.ui.search.TheSearchView;

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
                .map(IngredientResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchView::ShowIngredients, throwable -> searchView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCategories() {
        Disposable disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .map(CategoryResponse::getCategories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchView::showCategories, throwable -> searchView.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }

    @Override
    public void getCountries() {
       Disposable disposable = repository.getCountries()
               .subscribeOn(Schedulers.io())
               .map(CountryResponse::getCountries)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(searchView::showCountries, throwable -> searchView.showError(throwable.getMessage()));
       compositeDisposable.add(disposable);

    }
}
