package com.example.foodplanner.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private static final String TAG = "HomeAdapter";
    Context context;
    ArrayList<Category> categories;
    private OnCategoryClickListener onCategoryClickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }

    public CategoryAdapter(Context context, ArrayList<Category> categories,OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.categories= categories;
        this.onCategoryClickListener = onCategoryClickListener;

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
        Category categoriesItem = categories.get(position);
        holder.categoryName.setText(categoriesItem.getCategoryName());
        Glide.with(context).load(categoriesItem.getCategoryImage()).into(holder.categoryImage);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("category_name", categoriesItem.getCategoryName());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_mealFilteringFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.name);
            categoryImage = itemView.findViewById(R.id.image);
        }
    }
}

