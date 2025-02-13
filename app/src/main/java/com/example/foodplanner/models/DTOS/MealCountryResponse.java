package com.example.foodplanner.models.DTOS;

import java.util.ArrayList;

public class MealCountryResponse {
    private ArrayList<MealSpecification> meals;
    public ArrayList<MealSpecification> getMealsFromCountry(){
        return meals;
    }
}
