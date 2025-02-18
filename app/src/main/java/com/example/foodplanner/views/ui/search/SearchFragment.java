package com.example.foodplanner.views.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Ingredient;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.search.SearchPresenter;
import com.example.foodplanner.presenters.search.SearchPresenterImplementation;
import com.example.foodplanner.views.adapters.UniversalAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements TheSearchView {
    private SearchView searchView;
    private RecyclerView searchRecyclerView;
    private ChipGroup chipGroup;
    private Chip ingredientsChip, countriesChip, categoriesChip;
    ImageView emptySearch;
    TextView emptySearchText;

    private SearchPresenter searchPresenter;
    private UniversalAdapter universalAdapter;
    private final ArrayList<Object> allItems = new ArrayList<>();
    private final ArrayList<Object> filteredItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        searchRecyclerView = view.findViewById(R.id.SearchRecyclerView);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        emptySearch = view.findViewById(R.id.searchImg);
        emptySearchText = view.findViewById(R.id.emptySearch);

        chipGroup = view.findViewById(R.id.chipGroup);
        ingredientsChip = view.findViewById(R.id.ingredientsChip);
        countriesChip = view.findViewById(R.id.countriesChip);
        categoriesChip = view.findViewById(R.id.categoriesChip);

        Repository repository = Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext()));
        searchPresenter = new SearchPresenterImplementation(this, repository);

        universalAdapter = new UniversalAdapter(getContext(), filteredItems, item -> {
            if (item instanceof Ingredient) {
                navigateToMealFregment(((Ingredient) item).getStrIngredient(), "ingredient");
            } else if (item instanceof Category) {
                navigateToMealFregment(((Category) item).getCategoryName(), "category");
            } else if (item instanceof Country) {
                navigateToMealFregment(((Country) item).getCountryName(), "country");
            }
        });

        searchRecyclerView.setAdapter(universalAdapter);
        visibility(View.GONE, View.VISIBLE);

        fetchAllData();
        setupSearchView();
        setupChipGroup();
    }


    private void fetchAllData() {
        allItems.clear();
        filteredItems.clear();
        searchPresenter.getIngredients();
        searchPresenter.getCategories();
        searchPresenter.getCountries();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                if (newText.isEmpty()) {
                    visibility(View.GONE, View.VISIBLE);
                } else {
                    visibility(View.VISIBLE, View.GONE);
                }
                return true;
            }
        });
    }

    private void setupChipGroup() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            filterData(searchView.getQuery().toString());
        });
    }

    private void filterData(String query) {
        query = query.toLowerCase();
        filteredItems.clear();

        for (Object item : allItems) {
            boolean matchesQuery = false;

            if (item instanceof Ingredient) {
                matchesQuery = ((Ingredient) item).getStrIngredient().toLowerCase().contains(query);
            } else if (item instanceof Category) {
                matchesQuery = ((Category) item).getCategoryName().toLowerCase().contains(query);
            } else if (item instanceof Country) {
                matchesQuery = ((Country) item).getCountryName().toLowerCase().contains(query);
            }

            if (matchesQuery) {
                if (ingredientsChip.isChecked() && item instanceof Ingredient) {
                    filteredItems.add(item);
                    visibility(View.VISIBLE, View.GONE);
                } else if (categoriesChip.isChecked() && item instanceof Category) {
                    filteredItems.add(item);
                    visibility(View.VISIBLE, View.GONE);
                } else if (countriesChip.isChecked() && item instanceof Country) {
                    filteredItems.add(item);
                    visibility(View.VISIBLE, View.GONE);
                } else if (!ingredientsChip.isChecked() && !categoriesChip.isChecked() && !countriesChip.isChecked()) {
                    filteredItems.add(item);
                    searchRecyclerView.setVisibility(View.GONE);
                    emptySearch.setVisibility(View.VISIBLE);
                    emptySearchText.setVisibility(View.VISIBLE);
                }
            }
        }

        universalAdapter.updateData(filteredItems);
    }

    private void visibility(int visible, int gone) {
        searchRecyclerView.setVisibility(visible);
        emptySearch.setVisibility(gone);
        emptySearchText.setVisibility(gone);
    }

    @Override
    public void ShowIngredients(ArrayList<Ingredient> ingredients) {
        allItems.addAll(ingredients);
        filterData(searchView.getQuery().toString());
    }

    @Override
    public void showCategories(ArrayList<Category> categories) {
        allItems.addAll(categories);
        filterData(searchView.getQuery().toString());
    }

    @Override
    public void showCountries(ArrayList<Country> countries) {
        allItems.addAll(countries);
        filterData(searchView.getQuery().toString());
    }

    @Override
    public void showError(String errorMsg) {
        Log.i("TAG", "showError: " + errorMsg);
    }

    void navigateToMealFregment(String id, String type) {
        NavController navController = Navigation.findNavController(requireView());
        SearchFragmentDirections.ActionSearchFragmentToMealFilteringFragment action = SearchFragmentDirections.actionSearchFragmentToMealFilteringFragment(id, type);
        navController.navigate(action);
    }

}