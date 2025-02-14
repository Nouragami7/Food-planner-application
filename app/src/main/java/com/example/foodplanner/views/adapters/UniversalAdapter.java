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
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class UniversalAdapter extends RecyclerView.Adapter<UniversalAdapter.GenericViewHolder> {
    private Context context;
    private List<Object> items;

    public UniversalAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_search, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        Object item = items.get(position);

        if (item instanceof Ingredient) {
            holder.itemName.setText(((Ingredient) item).getStrIngredient());
        } else if (item instanceof Category) {
            holder.itemName.setText(((Category) item).getCategoryName());
            holder.loadImage(((Category) item).getCategoryImage());
        } else if (item instanceof Country) {
            holder.itemName.setText(((Country) item).getCountryName());
            String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/" +
                    ((Country) item).getCountryName().substring(0, 2).toLowerCase() + ".png";
            holder.loadImage(flagUrl);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Object> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;

        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.ingredientName);
            itemImage = itemView.findViewById(R.id.ingredientImg);
        }

        public void loadImage(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                itemImage.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(imageUrl).into(itemImage);
            } else {
                itemImage.setVisibility(View.GONE);
            }
        }
    }
}
