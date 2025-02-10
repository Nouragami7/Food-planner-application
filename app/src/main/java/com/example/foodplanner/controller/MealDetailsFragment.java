package com.example.foodplanner.controller;

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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.view.IngredientsAdapter;


public class MealDetailsFragment extends Fragment {

    ImageView mealImage;
    TextView mealName;
    TextView steps;
    TextView mealCountry;
    WebView videoWebView;

    IngredientsAdapter adapter;
    RecyclerView ingredientsRecycler;


    public MealDetailsFragment() {
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


        int id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();
        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();

        if (meal != null) {
            Log.i("TAG", "onViewCreated: " + meal.getStrMeal());

            Glide.with(getContext()).load(meal.getStrMealThumb()).into(mealImage);
            mealName.setText(meal.getStrMeal());
            mealCountry.setText(meal.getStrArea());
            steps.setText(meal.getStrInstructions());
            adapter = new IngredientsAdapter(getContext(), meal.getNonNullIngredients(),meal.getNonNullMeasurements());
            ingredientsRecycler.setAdapter(adapter);
            loadYouTubeVideo(meal.getStrYoutube());

        } else {
            Log.i("TAG", "onViewCreated: " + id);
        }



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

}