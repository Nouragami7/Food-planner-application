package com.example.foodplanner.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealSpecificationResponse {

	@SerializedName("meals")
	private List<MealSpecification> meals;

	public void setMeals(List<MealSpecification> meals){
		this.meals = meals;
	}

	public List<MealSpecification> getMeals(){
		return meals;
	}
}