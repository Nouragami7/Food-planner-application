package com.example.foodplanner.views.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.home.HomePresenterImplementation;
import com.example.foodplanner.views.adapters.CategoryAdapter;
import com.example.foodplanner.views.adapters.CountryAdapter;
import com.example.foodplanner.views.adapters.DailyInspirationAdapter;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView {

    private CarouselRecyclerview dailyMealRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView countryRecyclerView;
    private HomePresenterImplementation homePresenter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailyMealRecyclerView = view.findViewById(R.id.dailyRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        countryRecyclerView = view.findViewById(R.id.countryRecyclerView);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        countryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance());
        homePresenter = new HomePresenterImplementation(this, repository);

        loadHomeData();
    }

    private void loadHomeData() {
        homePresenter.getMeals();
        homePresenter.getCategories();
        homePresenter.getCountries();
    }

    @Override
    public void showMeals(ArrayList<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            DailyInspirationAdapter dailyInspirationAdapter = new DailyInspirationAdapter(getContext(), meals);
            dailyMealRecyclerView.setAdapter(dailyInspirationAdapter);
            dailyMealRecyclerView.setAlpha(true);
            dailyMealRecyclerView.setInfinite(false);
            dailyInspirationAdapter.setOnItemClickListener(meal -> navigateToMealDetails(Integer.parseInt(meal.getIdMeal()), meal));
        } else {
            showError("No meals found");
        }
    }

    @Override
    public void showCategories(ArrayList<Category> categories) {
            if (categories != null && !categories.isEmpty()) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, categoryName -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("category_name", categoryName);
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_mealFilteringFragment, bundle);
                });
                categoryRecyclerView.setAdapter(categoryAdapter);
            } else {
                showError("No categories found");
            }
    }

    @Override
    public void showCountries(ArrayList<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            CountryAdapter countryAdapter = new CountryAdapter(getContext(), countries, countryName -> {
                Bundle bundle = new Bundle();
                bundle.putString("country_name", countryName);
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_mealFilteringFragment, bundle);
            });
            countryRecyclerView.setAdapter(countryAdapter);
        } else {
            showError("No countries found");
        }
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMealDetails(int mealId, Meal meal) {
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId, meal)
            );
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
