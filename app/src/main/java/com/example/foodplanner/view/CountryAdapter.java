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
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {
    private static final String TAG = "HomeAdapter";
    Context context;
    ArrayList<Country> countries;

    public CountryAdapter(Context context,  ArrayList<Country> countries) {
        this.context = context;
        this.countries = countries;

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
        Country country = countries.get(position);
        holder.countryName.setText(country.getCountryName());
       // Glide.with(context).load(categoriesItem.getCategoryImage()).into(holder.countryImage);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;

        ImageView countryImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.name);
            countryImage = itemView.findViewById(R.id.image);
        }
    }
}


