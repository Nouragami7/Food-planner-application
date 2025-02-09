package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;


public class MealDetailsFragment extends Fragment {

    ImageView mealImage;
    TextView mealName;
    TextView mealCountry;




    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealCountry = view.findViewById(R.id.countryName);
        int id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();
        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();
        if (meal != null) {
            Log.i("TAG", "onViewCreated: "+meal.getStrMeal());

        }else{
            Log.i("TAG", "onViewCreated: "+id);

        }
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealCountry.setText(meal.getStrArea());


    }
}