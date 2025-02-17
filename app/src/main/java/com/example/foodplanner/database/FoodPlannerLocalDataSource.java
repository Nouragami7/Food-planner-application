package com.example.foodplanner.database;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.models.database.MealStorage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodPlannerLocalDataSource {
    private static FoodPlannerLocalDataSource foodPlannerLocalDataSource;
    private MealDAO mealDAO;
    private ApplicationDatabase database;

    SharedPreferences sharedPreferences;
    DatabaseReference myRef;
    FirebaseDatabase mealDatabase;

    private FoodPlannerLocalDataSource(Context context) {
        database = ApplicationDatabase.getInstance(context);
        this.database = database;
        mealDAO = database.mealDAO();
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mealDatabase = FirebaseDatabase.getInstance();
        myRef = mealDatabase.getReference("Meals");
    }
    public static FoodPlannerLocalDataSource getInstance(Context context) {
        if (foodPlannerLocalDataSource == null) {
            foodPlannerLocalDataSource = new FoodPlannerLocalDataSource(context);
        }
        return foodPlannerLocalDataSource;
    }
    public Single<List<MealStorage>> getAllFavouriteMeals() {
        return mealDAO.getAllFavouriteMeals();
    }
    public Single<List<MealStorage>> getAllPlannedMeals() {
        return mealDAO.getAllPlannedMeals();
    }
    public Completable insertMeal(MealStorage mealStorage) {
        return mealDAO.insertMeal(mealStorage);
    }

    public Completable deleteMeal(MealStorage mealStorage) {
        return mealDAO.deleteMeal(mealStorage);
    }

    public Completable clearAllData() {
        return mealDAO.deleteAllMeals();
    }


    public void fetchDataFromFirebase() {
        String userId = sharedPreferences.getString("userId", null);
        myRef.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("MainActivity", "onDataChange: " + dataSnapshot.getChildren());
                for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dateSnapshot : mealSnapshot.getChildren()) {
                      MealStorage savedMeals = dateSnapshot.getValue(MealStorage.class);
                        if (savedMeals != null) {
                            mealDAO.insertMeal(savedMeals).subscribeOn(Schedulers.io()).subscribe();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SavedMealsRepository", "Error fetching data from Firebase", error.toException());
            }
        });
    }

}
