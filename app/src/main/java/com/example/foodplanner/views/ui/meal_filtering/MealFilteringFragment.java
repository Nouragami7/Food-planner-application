package com.example.foodplanner.views.ui.meal_filtering;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.mealfiltering.MealFilteringPresenterImplementation;
import com.example.foodplanner.views.adapters.MealsListAdapter;

import java.util.ArrayList;

public class MealFilteringFragment extends Fragment implements MealFilteringView {
    private RecyclerView mealsRecyclerView;
    private MealsListAdapter mealsListAdapter;
    private MealFilteringPresenterImplementation mealFilteringPresenter;
    private String categoryName;
    private String countryName;

    public MealFilteringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_filtering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealsRecyclerView = view.findViewById(R.id.mealFilteringRecyclerView);
        mealsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance());
        mealFilteringPresenter = new MealFilteringPresenterImplementation(this, repository);

        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name", "");
            countryName = getArguments().getString("country_name", "");
            mealFilteringPresenter.getMealsByCategory(categoryName);
            mealFilteringPresenter.getMealsByCountry(countryName);
        }
    }
    @Override
    public void showMealsByCategory(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(getContext(), meals);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            showError("No meals found");
        }
    }

    @Override
    public void showMealsByCountry(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(getContext(), meals);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            showError("No meals found");
        }

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
