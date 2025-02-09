package com.example.foodplanner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private static final String TAG = "HomeAdapter";
    Context context;
    ArrayList<Meal> meals;
    //setter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    CardView cardView;

    public HomeAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal mealsItem = meals.get(position);
        holder.mealName.setText(mealsItem.getStrMeal());
        Glide.with(context).load(mealsItem.getStrMealThumb()).into(holder.mealImage);
        holder.randomIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClicks(mealsItem);
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
        CardView randomIdCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.name);
            mealImage = itemView.findViewById(R.id.image);
            randomIdCard = itemView.findViewById(R.id.randomIdCard);
        }
    }

    public interface OnItemClickListener{
        public void onClicks(Meal meal);

    }
}
