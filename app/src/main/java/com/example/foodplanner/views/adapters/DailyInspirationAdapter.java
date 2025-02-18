package com.example.foodplanner.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.models.DTOS.Meal;

import java.util.ArrayList;
import java.util.Objects;

public class DailyInspirationAdapter extends RecyclerView.Adapter<DailyInspirationAdapter.MyViewHolder> {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<Meal> meals;

    SharedPreferences sharedPreferences;
    OnItemClickListener onItemClickListener;
    CardView cardView;

    public DailyInspirationAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    //setter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.daily_inspiration, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal mealsItem = meals.get(position);
        holder.mealName.setText(mealsItem.getStrMeal());
        Glide.with(context).load(mealsItem.getStrMealThumb()).into(holder.mealImage);

        holder.randomIdCard.setOnClickListener(v -> {
            if (sharedPreferences.getString("userId", "guest").equals("guest")) {
                showDialog("Oops! 🤔\nYou need to sign up first \nto explore this delicious meal. 🍽️");
            } else {
                onItemClickListener.onClicks(mealsItem);
            }
        });
    }

    public void showDialog(String message) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context); // Fix context issue
        builder.setView(dialogView);

        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button dialogButton = dialogView.findViewById(R.id.dialogButton);

        dialogMessage.setText(message);
        dialogButton.setOnClickListener(v -> alertDialog.dismiss());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public interface OnItemClickListener {
        void onClicks(Meal meal);

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
}
