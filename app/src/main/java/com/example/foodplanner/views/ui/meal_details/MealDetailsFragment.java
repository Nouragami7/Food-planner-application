package com.example.foodplanner.views.ui.meal_details;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.foodplanner.R;

import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.Repository.Repository;
import com.example.foodplanner.network.FoodPlannerRemoteDataSource;
import com.example.foodplanner.presenters.mealdetails.MealDetailsPresenterImplementation;
import com.example.foodplanner.views.adapters.IngredientsAdapter;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private ImageView mealImage;
    private TextView mealName, steps, mealCountry;
    private WebView videoWebView;
    private IngredientsAdapter adapter;
    private RecyclerView ingredientsRecycler;
    private MealDetailsPresenterImplementation presenter;
    private int mealId;

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
        ingredientsRecycler = view.findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        presenter = new MealDetailsPresenterImplementation(this, Repository.getInstance(FoodPlannerRemoteDataSource.getInstance()));

        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();
        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (meal != null) {
            Log.i("MealDetailsFragment", "Meal received: " + meal.getStrMeal());
            displayMealDetails(meal);
        } else {
            Log.w("MealDetailsFragment", "Meal object is null, fetching from API...");
            presenter.getMealDetailsById(mealId);
        }
    }

    private void displayMealDetails(Meal meal) {
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealCountry.setText(meal.getStrArea());
        steps.setText(meal.getStrInstructions());

        adapter = new IngredientsAdapter(getContext(), meal.getNonNullIngredients(), meal.getNonNullMeasurements());
        ingredientsRecycler.setAdapter(adapter);

        loadYouTubeVideo(meal.getStrYoutube());
    }

    private void loadYouTubeVideo(String youtubeUrl) {
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        videoWebView.setWebChromeClient(new WebChromeClient());

        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null) {
            String iframe = "<iframe width=\"100%\" height=\"100%\" "+
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
            Log.d("MealDetailsFragment", "Meal received from API: " + meal.getStrMeal());
            displayMealDetails(meal);
        } else {
            showError("Meal not found");
        }
    }

    @Override
    public void showError(String message) {
        Log.e("MealDetailsFragment", "Error: " + message);
    }

}
