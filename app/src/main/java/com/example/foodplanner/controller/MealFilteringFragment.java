package com.example.foodplanner.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealSpecification;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.network.RetrofitClient;
import com.example.foodplanner.view.MealsListAdapter;

import java.util.ArrayList;

public class MealFilteringFragment extends Fragment implements NetworkCallback {
    RecyclerView mealsRecyclerView;
    MealsListAdapter mealsListAdapter;
    RetrofitClient retrofitClient;
    String categoryName;

    public MealFilteringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_filtering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealsRecyclerView = view.findViewById(R.id.mealFilteringRecyclerView);
        mealsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        retrofitClient = RetrofitClient.getInstance();

        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name", "");
            fetchMealsByCategory(categoryName);
        }

    }

    private void fetchMealsByCategory(String category) {
        retrofitClient.makeNetworkCallMealSpecificationByCategory(this, category);
    }

    @Override
    public void onSuccessResult(ArrayList<Meal> meals) {

    }

    @Override
    public void onSuccessResultCategory(ArrayList<Category> Categories) {

    }

    @Override
    public void onSuccesResultArea(ArrayList<Country> meals) {

    }

    @Override
    public void onSuccessResultMealSpecification(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(getContext(), meals);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }
}