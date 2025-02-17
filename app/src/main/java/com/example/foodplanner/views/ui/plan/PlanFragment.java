package com.example.foodplanner.views.ui.plan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.interfacies.OnSpecifiedMealClickListener;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.plan.PlanPresenterImplementation;
import com.example.foodplanner.views.adapters.FavouriteAdapter;
import com.example.foodplanner.views.adapters.PlanAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements PlanView, OnSpecifiedMealClickListener {
    private PlanPresenterImplementation presenter;

    RecyclerView saturdayRecyclerView, sundayRecyclerView, mondayRecyclerView, tuesdayRecyclerView, wednesdayRecyclerView, thursdayRecyclerView, fridayRecyclerView;
    List<MealStorage> stardayList= new ArrayList<>();
    List<MealStorage> sundayList= new ArrayList<>();
    List<MealStorage> mondayList= new ArrayList<>();
    List<MealStorage> tuesdayList= new ArrayList<>();
    List<MealStorage> wednesdayList= new ArrayList<>();
    List<MealStorage> thursdayList= new ArrayList<>();
    List<MealStorage> fridayList= new ArrayList<>();
    PlanAdapter planAdapter;








    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext()));
        presenter = new PlanPresenterImplementation(this, repository);
        saturdayRecyclerView = view.findViewById(R.id.recyclerViewSaturday);
        sundayRecyclerView = view.findViewById(R.id.recyclerViewSunday);
        mondayRecyclerView = view.findViewById(R.id.recyclerViewMonday);
        tuesdayRecyclerView = view.findViewById(R.id.recyclerViewTuesday);
        wednesdayRecyclerView = view.findViewById(R.id.recyclerViewWednesday);
        thursdayRecyclerView = view.findViewById(R.id.recyclerViewThursday);
        fridayRecyclerView = view.findViewById(R.id.recyclerViewFriday);

        presenter.getAllMealsFromPlan();
    }

    @Override
    public void showDataSuccess(List<MealStorage> mealStorages) {
        for (MealStorage mealStorage : mealStorages) {
            switch (mealStorage.getDate()) {
                case "Saturday":
                    stardayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), stardayList,this);
                    saturdayRecyclerView.setAdapter(planAdapter);
                    saturdayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Sunday":
                    sundayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), sundayList,this);
                    sundayRecyclerView.setAdapter(planAdapter);
                    sundayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Monday":
                    mondayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), mondayList,this);
                    mondayRecyclerView.setAdapter(planAdapter);
                    mondayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Tuesday":
                    tuesdayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), tuesdayList,this);
                    tuesdayRecyclerView.setAdapter(planAdapter);
                    tuesdayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Wednesday":
                    wednesdayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), wednesdayList,this);
                    wednesdayRecyclerView.setAdapter(planAdapter);
                    wednesdayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Thursday":
                    thursdayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), thursdayList,this);
                    thursdayRecyclerView.setAdapter(planAdapter);
                    thursdayRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case "Friday":
                    fridayList.add(mealStorage);
                    planAdapter = new PlanAdapter(getContext(), fridayList,this);
                    fridayRecyclerView.setAdapter(planAdapter);
                    fridayRecyclerView.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onMealClick(int id, Meal meal) {
        NavController navController = Navigation.findNavController(requireView());
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action = PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(id, meal);
        navController.navigate(action);

    }
}