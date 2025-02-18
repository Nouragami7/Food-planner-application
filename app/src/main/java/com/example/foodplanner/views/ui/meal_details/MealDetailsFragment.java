package com.example.foodplanner.views.ui.meal_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private ImageView mealImage;
    private TextView mealName, steps, mealCountry;
    private WebView videoWebView;
    private RecyclerView ingredientsRecycler;
    private SharedPreferences sharedPreferences;
    private Boolean isFavourite = false;
    private Boolean isPlan = false;
    private MealDetailsPresenterImplementation presenter;
    private Button favouriteButton;
    private Button planButton;

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

        isFavourite = MealDetailsFragmentArgs.fromBundle(getArguments()).getIsFavourite();
        isPlan = MealDetailsFragmentArgs.fromBundle(getArguments()).getIsPlanned();

        presenter = new MealDetailsPresenterImplementation(
                this,
                Repository.getInstance(
                        FoodPlannerRemoteDataSource.getInstance(),
                        FoodPlannerLocalDataSource.getInstance(requireContext())
                ),
                this.getContext()
        );

        Meal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getRandomMeal();
        int mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (meal != null) {
            displayMealDetails(meal);
        } else {
            presenter.getMealDetailsById(mealId);
        }
        updateSaveButtonState(isFavourite);
        updatePlanButtonState(isPlan);
    }

    private void showDatePicker(Meal meal) {
        Calendar calendar = Calendar.getInstance();
        long startOfWeek = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
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
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        datePicker.show(getParentFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            selectedCalendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = format.format(selectedCalendar.getTime());
            saveMealToPlan(meal, selectedDate);
        });
    }

    private void saveMealToPlan(Meal meal, String selectedDate) {
        String userId = sharedPreferences.getString("userId", null);
        if (userId != null && !userId.isEmpty()) {
            String date = DataConverter.getFormattedDate(selectedDate);
            MealStorage mealStorage = new MealStorage(true, false, meal, date, userId, meal.getIdMeal());
            presenter.addToPlan(mealStorage);
            presenter.sendData(mealStorage);
            updateButtonState(planButton, true, "Added to Plan", R.color.dark_purple);
            planButton.setEnabled(false);
            isPlan = true;
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

        updateButtonState(favouriteButton, isFavourite, isFavourite ? "Added to Favourites" : "Add to Favourites", isFavourite ? R.color.dark_purple : R.color.dark_pink);
        updateButtonState(planButton, isPlan, isPlan ? "Added to Plan" : "Add to Plan", isPlan ? R.color.dark_purple : R.color.light_purple);

        favouriteButton.setOnClickListener(v -> {
            String userId = sharedPreferences.getString("userId", null);
            if (userId != null && !userId.isEmpty()) {
                if (isFavourite) {
                    MealStorage mealStorage = new MealStorage(false, true, meal, "Favourite", userId, meal.getIdMeal());
                    presenter.deleteMealFromFavourite(mealStorage);
                    updateButtonState(favouriteButton, false, "Add to Favourites", R.color.dark_pink);
                    isFavourite = false;
                } else {
                    MealStorage mealStorage = new MealStorage(false, true, meal, "Favourite", userId, meal.getIdMeal());
                    presenter.addToFavourite(mealStorage);
                    presenter.sendData(mealStorage);
                    updateButtonState(favouriteButton, true, "Added to Favourites", R.color.dark_purple);
                    isFavourite = true;
                }
            } else {
                Toast.makeText(getContext(), "Please login to add to favourites", Toast.LENGTH_SHORT).show();
            }
        });

        planButton.setOnClickListener(v -> {
            showDatePicker(meal);

        });
    }
    @SuppressLint("ResourceAsColor")
    private void updateSaveButtonState(boolean isSaved) {
        if (isSaved) {
            favouriteButton.setText(R.string.added_to_favourites);
            favouriteButton.setBackgroundColor(getResources().getColor(R.color.dark_purple));
        } else {
            favouriteButton.setText(R.string.add_to_favourites);
            favouriteButton.setBackgroundColor(getResources().getColor(R.color.dark_pink));
        }
        favouriteButton.setEnabled(true);
    }

    @SuppressLint("ResourceAsColor")
    private void updatePlanButtonState(boolean isPlanned) {
        if (isPlanned) {
            planButton.setText(R.string.added_to_plan);
            planButton.setBackgroundColor(getResources().getColor(R.color.dark_purple));
            planButton.setEnabled(false);
        }

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
            showError(getString(R.string.meal_not_found));
        }
    }

    @Override
    public void showSuccessMessage(String message) {
        showSnackBar(message);
    }

    @Override
    public void deleteMealFromFavourite(MealStorage mealStorage) {
        presenter.deleteMealFromFavourite(mealStorage);
    }

    @Override
    public void showError(String message) {
        Log.e("MealDetailsFragment", "Error: " + message);
    }

    private void updateButtonState(Button button, boolean isAdded, String text, int color) {
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(color));
        button.setEnabled(true);
    }

    private void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        int color = ContextCompat.getColor(requireContext(), R.color.light_pink);
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}