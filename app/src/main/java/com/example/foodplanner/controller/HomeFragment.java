package com.example.foodplanner.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.foodplanner.network.RetrofitClient;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.view.CategoryAdapter;
import com.example.foodplanner.view.CountryAdapter;
import com.example.foodplanner.view.HomeAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements NetworkCallback {

   RecyclerView dailyMealRecyclerView;
   RecyclerView categoryRecyclerView;

   RecyclerView countryRecyclerView;
   HomeAdapter homeAdapter;

   CategoryAdapter categoryAdapter;
   CountryAdapter countryAdapter;
   RetrofitClient retrofitClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dailyMealRecyclerView = view.findViewById(R.id.dailyRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        countryRecyclerView = view.findViewById(R.id.countryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dailyMealRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        retrofitClient = RetrofitClient.getInstance();
        retrofitClient.makeNetworkCall(this, 10);
        retrofitClient.makeNetworkCallCategory(this);
        retrofitClient.makeNetworkCallArea(this, "list");
    }

    @Override
    public void onSuccessResult(ArrayList<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            homeAdapter = new HomeAdapter(getContext(), meals);
            dailyMealRecyclerView.setAdapter(homeAdapter);
            Toast.makeText(getContext(), "Meals loaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessResultCategory(ArrayList<Category> categories) {
        if (categories != null && !categories.isEmpty()) {
            categoryAdapter = new CategoryAdapter(getContext(), categories);
            categoryRecyclerView.setAdapter(categoryAdapter);
            Toast.makeText(getContext(), "Categories loaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No categories found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccesResultArea(ArrayList<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            countryAdapter = new CountryAdapter(getContext(), countries);
            countryRecyclerView.setAdapter(countryAdapter);
            Toast.makeText(getContext(), "Countries loaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No countries found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();

    }
}