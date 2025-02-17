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
import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.interfacies.OnCategoryClickListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>  {
    private static final String TAG = "DailyInspirationAdapter";
    Context context;
    ArrayList<Category> categories;
    private OnCategoryClickListener onCategoryClickListener;

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
        holder.itemView.setOnClickListener(v -> onCategoryClickListener.onCategoryClick(categories.get(position).getCategoryName()));

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
            categoryName = itemView.findViewById(R.id.myName);
            categoryImage = itemView.findViewById(R.id.myImage);
        }
    }
    public void updateData(ArrayList<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }
}

