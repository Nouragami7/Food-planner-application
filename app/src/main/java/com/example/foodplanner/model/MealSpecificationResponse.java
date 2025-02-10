package com.example.foodplanner.model;

import java.util.ArrayList;

public class MealSpecificationResponse {

	private ArrayList<MealSpecification> meals;
	public ArrayList<MealSpecification> getMealsFromCategory(){
		return meals;
	}
}