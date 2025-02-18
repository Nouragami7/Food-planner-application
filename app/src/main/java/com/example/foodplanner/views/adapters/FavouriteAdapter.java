package com.example.foodplanner.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.interfacies.OnMealDeleteListener;
import com.example.foodplanner.interfacies.OnSpecifiedMealClickListener;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteMealViewHolder> {
    private final Context context;
    private final List<MealStorage> favouriteMeals;
    private final OnMealDeleteListener deleteListener;
    private final OnSpecifiedMealClickListener onFavouriteMealClickListener;

    public FavouriteAdapter(Context context, List<MealStorage> favouriteMeals, OnMealDeleteListener deleteListener, OnSpecifiedMealClickListener onFavouriteMealClickListener) {
        this.context = context;
        this.favouriteMeals = favouriteMeals;
        this.deleteListener = deleteListener;
        this.onFavouriteMealClickListener = onFavouriteMealClickListener;
    }

    @NonNull
    @Override
    public FavouriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_at_favourite, parent, false);
        return new FavouriteMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealViewHolder holder, int position) {
        MealStorage mealStorage = favouriteMeals.get(position);
        Meal meal = mealStorage.getMeal();

        holder.mealName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onMealDelete(mealStorage);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (onFavouriteMealClickListener != null) {
                onFavouriteMealClickListener.onMealClick(Integer.parseInt(meal.getIdMeal()), meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteMeals.size();
    }

    public static class FavouriteMealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        Button deleteButton;

        public FavouriteMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealListName);
            mealImage = itemView.findViewById(R.id.mealListImage);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }

}
