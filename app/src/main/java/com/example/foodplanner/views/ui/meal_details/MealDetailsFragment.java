package com.example.foodplanner.views.ui.meal_details;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;

import com.example.foodplanner.database.FoodPlannerLocalDataSource;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.mealdetails.MealDetailsPresenterImplementation;
import com.example.foodplanner.views.adapters.IngredientsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private ImageView mealImage;
    private TextView mealName, steps, mealCountry;
    private WebView videoWebView;
    private RecyclerView ingredientsRecycler;
    SharedPreferences sharedPreferences;
    private int mealId;
    MealDetailsPresenterImplementation presenter;
    Button favouriteButton;
    Button planButton;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealImage = view.findViewById(R.id.mealListImage);
        mealName = view.findViewById(R.id.mealListName);
        mealCountry = view.findViewById(R.id.countryName);
        steps = view.findViewById(R.id.instructions);
        videoWebView = view.findViewById(R.id.videoView);
        favouriteButton = view.findViewById(R.id.btnAddToFavourite);
        planButton = view.findViewById(R.id.btnAddToPlan);
        ingredientsRecycler = view.findViewById(R.id.ingredientsRecyclerView);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        presenter = new MealDetailsPresenterImplementation(this, Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext())));

        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();
        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (meal != null) {
            displayMealDetails(meal);
        } else {
            presenter.getMealDetailsById(mealId);
        }

    }

    private void displayMealDetails(Meal meal) {
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealCountry.setText(meal.getStrArea());
        steps.setText(meal.getStrInstructions());
        IngredientsAdapter adapter = new IngredientsAdapter(getContext(), meal.getNonNullIngredients(), meal.getNonNullMeasurements());
        ingredientsRecycler.setAdapter(adapter);
        loadYouTubeVideo(meal.getStrYoutube());
        favouriteButton.setOnClickListener(v -> {
            String userId= sharedPreferences.getString("userId",null);
            if (userId != null && !userId.isEmpty()) {
            MealStorage mealStorage = new MealStorage(false,true,meal,"Favourite", userId, meal.getIdMeal());
            presenter.addToFavourite(mealStorage);
            }
            else {
                Toast.makeText(getContext(), "Please login to add to favourites", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadYouTubeVideo(String youtubeUrl) {
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        videoWebView.setWebChromeClient(new WebChromeClient());

        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null) {
            String iframe = "<iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=0&mute=0\" " +
                    "frameborder=\"0\" allowfullscreen></iframe>";

            videoWebView.loadData(iframe, "text/html", "utf-8");
        } else {
            videoWebView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMealDetailsById(Meal meal) {
        if (meal != null) {
            displayMealDetails(meal);
        } else {
            showError("Meal not found");
        }
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showError(String message) {
        Log.e("MealDetailsFragment", "Error: " + message);
    }

}
