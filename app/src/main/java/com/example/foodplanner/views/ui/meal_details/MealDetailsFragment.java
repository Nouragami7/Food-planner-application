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

import android.os.Parcel;
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
import com.example.foodplanner.utilts.DataConverter;
import com.example.foodplanner.views.adapters.IngredientsAdapter;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

        presenter = new MealDetailsPresenterImplementation(this, Repository.getInstance(FoodPlannerRemoteDataSource.getInstance(), FoodPlannerLocalDataSource.getInstance(requireContext())),this.getContext());

        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();
        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (meal != null) {
            displayMealDetails(meal);
        } else {
            presenter.getMealDetailsById(mealId);
        }

    }

    private void showDatePicker(Meal meal) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        long startOfWeek = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        long endOfWeek = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(startOfWeek);
        constraintsBuilder.setEnd(endOfWeek);
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= startOfWeek && date <= endOfWeek;
            }
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel parcel, int i) {

            }
        });
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a day this week")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Default selection: today
                .build();
        datePicker.show(getParentFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            selectedCalendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = format.format(selectedCalendar.getTime());
            saveMealToPlan(meal,selectedDate);
        });
    }

    private void saveMealToPlan(Meal meal, String selectedDate) {
        String userId= sharedPreferences.getString("userId",null);
        if (userId != null && !userId.isEmpty()) {
            String date = DataConverter.getFormattedDate(selectedDate);
            MealStorage mealStorage = new MealStorage(true,false,meal,date, userId, meal.getIdMeal());
            presenter.addToPlan(mealStorage);
            presenter.sendData(mealStorage);
            updateButtonState(planButton, true, "Added to Plan", R.color.dark_purple);
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
            presenter.sendData(mealStorage);
            updateButtonState(favouriteButton, true, "Added to Favourites", R.color.dark_purple);

            }
            else {
                Toast.makeText(getContext(), "Please login to add to favourites", Toast.LENGTH_SHORT).show();
            }
        });
        planButton.setOnClickListener(v -> {
            showDatePicker(meal);

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


    private void updateButtonState(Button button, boolean isDisabled, String text, int color) {
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(color));
        button.setEnabled(!isDisabled);
    }


}
