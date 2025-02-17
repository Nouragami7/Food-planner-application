package com.example.foodplanner.views.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.interfacies.NetworkStateListener;
import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.home.HomePresenterImplementation;
import com.example.foodplanner.views.adapters.CategoryAdapter;
import com.example.foodplanner.views.adapters.CountryAdapter;
import com.example.foodplanner.views.adapters.DailyInspirationAdapter;
import com.google.android.material.navigation.NavigationView;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView, NetworkStateListener {

    private CarouselRecyclerview dailyMealRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView countryRecyclerView;
    private HomePresenterImplementation homePresenter;
    LottieAnimationView lottieAnimationView;
    ImageView menuIcon;
    DrawerLayout drawerLayout;
    ScrollView home;

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
        lottieAnimationView = view.findViewById(R.id.waitingLottie);
        menuIcon = view.findViewById(R.id.menuIcon);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        home = view.findViewById(R.id.homeView);


        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.userName);
        TextView userEmailTextView = headerView.findViewById(R.id.userEmail);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("userName", "");
        String savedEmail = sharedPreferences.getString("userEmail", "");
        userNameTextView.setText(savedName);
        userEmailTextView.setText(savedEmail);


        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        countryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext()));
        homePresenter = new HomePresenterImplementation(this, repository,requireContext());

        ((MainActivity) requireActivity()).setNetworkStateListener(this);

        loadHomeData();

        navigationView.setNavigationItemSelectedListener(item -> {
            Log.i("TAG", "onViewCreated: " + item);
            if (item.getItemId() == R.id.nav_logout) {
                homePresenter.logout();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return false;
        });

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadHomeData() {
        homePresenter.getMeals();
        homePresenter.getCategories();
        homePresenter.getCountries();
        homePresenter.getDataFromFirebase();
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
                    HomeFragmentDirections.ActionHomeFragmentToMealFilteringFragment action = HomeFragmentDirections.actionHomeFragmentToMealFilteringFragment(categoryName, "category");
                    Navigation.findNavController(getView()).navigate(action);
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
               HomeFragmentDirections.ActionHomeFragmentToMealFilteringFragment action = HomeFragmentDirections.actionHomeFragmentToMealFilteringFragment(countryName, "country");
                Navigation.findNavController(getView()).navigate(action);
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

    @Override
    public void onLogoutSuccess() {
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_introductionFragment2);

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
        home.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        home.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkAvailable() {
        loadHomeData();
    }

    @Override
    public void onNetworkLost() {
        showError("Network is not available");
    }
}
