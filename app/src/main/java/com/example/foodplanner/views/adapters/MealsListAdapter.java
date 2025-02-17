package com.example.foodplanner.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.interfacies.OnMealClickListener;
import com.example.foodplanner.models.DTOS.MealSpecification;

import java.util.ArrayList;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MyViewHolder> {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<MealSpecification> meals;
    private OnMealClickListener onMealClickListener;
    SharedPreferences sharedPreferences;

    public MealsListAdapter(Context context, ArrayList<MealSpecification> meals, OnMealClickListener onMealClickListener) {
        this.context = context;
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_filtering, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealSpecification mealSpecification = meals.get(position);

        if (mealSpecification == null) {
            Log.e(TAG, "MealSpecification is null at position: " + position);
            return;
        }

        holder.mealName.setText(mealSpecification.getStrMeal());

        Glide.with(context)
                .load(mealSpecification.getStrMealThumb())
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> {
            if (sharedPreferences.getString("userId", "guest").equals("guest")) {
                Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show();
            } else {
                if (onMealClickListener != null) {
                    String mealId = mealSpecification.getIdMeal();
                    if (mealId != null) {
                        onMealClickListener.onMealClick(Integer.parseInt(mealId));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;

        ImageView mealImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealListName);
            mealImage = itemView.findViewById(R.id.mealListImage);
        }
    }
}
