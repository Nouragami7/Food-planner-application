package com.example.foodplanner.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.models.DTOS.Meal;

import java.util.ArrayList;

public class DailyInspirationAdapter extends RecyclerView.Adapter<DailyInspirationAdapter.MyViewHolder> {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<Meal> meals;

    SharedPreferences sharedPreferences;
    //setter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    CardView cardView;

    public DailyInspirationAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

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
        holder.randomIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getString("userId","guest").equals("guest")){
                    Toast.makeText(context, "Please login to save this meal", Toast.LENGTH_SHORT).show();
                }else{
                    onItemClickListener.onClicks(mealsItem);
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
