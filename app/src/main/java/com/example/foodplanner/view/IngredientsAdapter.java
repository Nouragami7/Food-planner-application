package com.example.foodplanner.view;

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
import com.example.foodplanner.model.Country;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<String> ingredients;
    ArrayList<String> measurements;

    public IngredientsAdapter(Context context, ArrayList<String> ingredients, ArrayList<String> measurements) {
        this.context = context;
        this.ingredients = ingredients;
        this.measurements = measurements;

    }

    @NonNull
    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient, parent, false);
        return new IngredientsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.MyViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        String measurement = measurements.get(position);
        holder.ingredientName.setText(ingredient);
        holder.measurementName.setText(measurement);
        String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + "-Small.png";
        Glide.with(context)
                .load(imageUrl)
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName, measurementName;

        ImageView ingredientImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientImage = itemView.findViewById(R.id.ingredientImg);
            measurementName = itemView.findViewById(R.id.measurementName);
        }
    }
}
