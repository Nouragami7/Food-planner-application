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
import com.example.foodplanner.models.DTOS.MealSpecification;

import java.util.ArrayList;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MyViewHolder> {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<MealSpecification> meals;

    public MealsListAdapter(Context context,  ArrayList<MealSpecification> meals) {
        this.context = context;
        this.meals=meals;

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
        holder.mealName.setText(mealSpecification.getStrMeal());
         Glide.with(context).load(mealSpecification.getStrMealThumb()).into(holder.mealImage);
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
