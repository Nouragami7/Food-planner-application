package com.example.foodplanner.presenters.plan;

import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.views.ui.plan.PlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImplementation implements PlanPresenter{

    public final PlanView planView;
    public final Repository repository;

    public PlanPresenterImplementation(PlanView planView, Repository repository) {
        this.planView = planView;
        this.repository = repository;
    }

    @Override
    public void getAllMealsFromPlan() {
        repository.getAllMealsFromPlan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealStorages -> {
                            if (mealStorages != null) {
                                planView.showDataSuccess(mealStorages);
                            } else {
                                planView.showError("No meals found");
                            }
                        });
}
}
