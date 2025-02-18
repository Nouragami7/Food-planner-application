package com.example.foodplanner.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.interfacies.OnSpecifiedMealClickListener;
import com.example.foodplanner.models.DTOS.Meal;
import com.example.foodplanner.models.database.MealStorage;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanMealViewHolder> {
    private final Context context;
    private final List<MealStorage> planMeals;
    OnSpecifiedMealClickListener onSpecifiedMealClickListener;


    public PlanAdapter(Context context, List<MealStorage> planMeals, OnSpecifiedMealClickListener onSpecifiedMealClickListener) {
        this.context = context;
        this.planMeals = planMeals;
        this.onSpecifiedMealClickListener = onSpecifiedMealClickListener;
    }

    @NonNull
    @Override
    public PlanAdapter.PlanMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_at_plan, parent, false);
        return new PlanAdapter.PlanMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanMealViewHolder holder, int position) {
        MealStorage mealStorage = planMeals.get(position);
        Meal meal = mealStorage.getMeal();

        holder.mealName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpecifiedMealClickListener.onMealClick(Integer.parseInt(meal.getIdMeal()), meal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return planMeals.size();
    }

    public static class PlanMealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public PlanMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.planMealName);
            mealImage = itemView.findViewById(R.id.planMealImg);
        }
    }

}

