package com.example.foodplanner.views.ui.favourite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.favourite.FavouritePresenterImplementation;
import com.example.foodplanner.views.adapters.FavouriteAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouriteView , OnMealDeleteListener {
    private static final String TAG = "FavouriteFragment";
    private FavouritePresenterImplementation favouritePresenterImplementation;

    RecyclerView favouriteRecyclerView;
    Group favGroup;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext()));
        favouritePresenterImplementation = new FavouritePresenterImplementation(this, repository, getContext());
        favouriteRecyclerView =view.findViewById(R.id.favouriteRecyclerView);
        favGroup = view.findViewById(R.id.fav_group);

        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favouritePresenterImplementation.getAllFavoriteMeals();

    }

    @Override
    public void showDataSuccess(List<MealStorage> mealStorage) {
        if (mealStorage.isEmpty()) {
            favGroup.setVisibility(View.VISIBLE);
            favouriteRecyclerView.setVisibility(View.GONE);
        } else {
            favGroup.setVisibility(View.GONE);
            favouriteRecyclerView.setVisibility(View.VISIBLE);
            FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(), mealStorage, this);
            favouriteRecyclerView.setAdapter(favouriteAdapter);
        }
    }


    @Override
    public void showSuccessMessage(MealStorage mealStorage) {
        favouritePresenterImplementation.getAllFavoriteMeals();


    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onMealDelete(MealStorage mealStorage) {
        favouritePresenterImplementation.deleteMealFromFavourite(mealStorage);
        favouritePresenterImplementation.deleteData(mealStorage);
        showSuccessMessage(mealStorage);
        Snackbar snackbar = Snackbar.make(favouriteRecyclerView, "Meal removed from favourites", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}