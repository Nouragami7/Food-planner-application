package com.example.foodplanner.views.ui.meal_filtering;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.DTOS.MealSpecification;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.mealfiltering.MealFilteringPresenterImplementation;
import com.example.foodplanner.views.adapters.MealsListAdapter;

import java.util.ArrayList;

public class MealFilteringFragment extends Fragment implements MealFilteringView {
    private RecyclerView mealsRecyclerView;
    private MealsListAdapter mealsListAdapter;

    LottieAnimationView lottieAnimationView;


    public MealFilteringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_filtering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealsRecyclerView = view.findViewById(R.id.mealFilteringRecyclerView);
        mealsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext()));
        MealFilteringPresenterImplementation mealFilteringPresenter = new MealFilteringPresenterImplementation(this, repository);
        lottieAnimationView = view.findViewById(R.id.waitingAnimation);

        MealFilteringFragmentArgs args = MealFilteringFragmentArgs.fromBundle(getArguments());
        String id = args.getMealId();
        String type = args.getType();
        if (type.equals("category")) {
            mealFilteringPresenter.getMealsByCategory(id);
            Log.i("TAG", "onViewCreated: " + id);
        } else if (type.equals("country")) {
            mealFilteringPresenter.getMealsByCountry(id);
        } else if (type.equals("ingredient")) {
            mealFilteringPresenter.getMealsByIngredient(id);
        }
    }

    @Override
    public void showMealsByCategory(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(requireContext(), meals, this::navigateToMealDetails);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            showError("no data");
        }
    }

    @Override
    public void showMealsByCountry(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(getContext(), meals, this::navigateToMealDetails);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            showError("No meals found");
        }
    }

    @Override
    public void showMealsByIngredient(ArrayList<MealSpecification> meals) {
        if (meals != null && !meals.isEmpty()) {
            mealsListAdapter = new MealsListAdapter(getContext(), meals, this::navigateToMealDetails);
            mealsRecyclerView.setAdapter(mealsListAdapter);
        } else {
            showError("No meals found");
        }
    }

    @Override
    public void showError(String errorMsg) {
        Log.i("TAG", "showError: " + errorMsg);
    }

    @Override
    public void showLoading() {
        lottieAnimationView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        lottieAnimationView.setVisibility(View.GONE);

    }

    public void navigateToMealDetails(int mealId) {
        NavController navController = Navigation.findNavController(requireView());
        MealFilteringFragmentDirections.ActionMealFilteringFragmentToMealDetailsFragment action = MealFilteringFragmentDirections.actionMealFilteringFragmentToMealDetailsFragment(mealId, null, false, false);
        navController.navigate(action);
    }
}
