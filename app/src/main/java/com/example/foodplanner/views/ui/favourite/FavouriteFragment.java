package com.example.foodplanner.views.ui.favourite;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.interfacies.OnSpecifiedMealClickListener;
import com.example.foodplanner.interfacies.OnMealDeleteListener;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.favourite.FavouritePresenterImplementation;
import com.example.foodplanner.views.adapters.FavouriteAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouriteView , OnMealDeleteListener, OnSpecifiedMealClickListener {
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
            FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(), mealStorage, this,this);
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
        showErrorSnackBar("Meal removed from favourites");
    }

    private void showErrorSnackBar(String message){
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        int color = ContextCompat.getColor(requireContext(), R.color.dark_pink);
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onMealClick(int id, Meal meal) {
        NavController navController = Navigation.findNavController(requireView());
        FavouriteFragmentDirections.ActionFavouriteFragmentToMealDetailsFragment action = FavouriteFragmentDirections.actionFavouriteFragmentToMealDetailsFragment(id, meal);
        navController.navigate(action);

    }
}