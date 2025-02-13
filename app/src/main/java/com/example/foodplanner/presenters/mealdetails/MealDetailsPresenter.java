package com.example.foodplanner.presenters.mealdetails;

public interface MealDetailsPresenter  {
    void getMealDetails();
    void onDestroy();
    void loadVideo(String youtubeUrl);
}
