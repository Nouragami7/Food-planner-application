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
import com.example.foodplanner.interfacies.OnItemListener;
import com.example.foodplanner.models.DTOS.Category;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.models.DTOS.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversalAdapter extends RecyclerView.Adapter<UniversalAdapter.GenericViewHolder> {
    private final Context context;
    private final List<Object> items;
    private final OnItemListener onItemListener;


    private static final Map<String, String> COUNTRY_CODE_MAP = new HashMap<>();
    static {
        COUNTRY_CODE_MAP.put("American", "us");
        COUNTRY_CODE_MAP.put("British", "gb");
        COUNTRY_CODE_MAP.put("Canadian", "ca");
        COUNTRY_CODE_MAP.put("Chinese", "cn");
        COUNTRY_CODE_MAP.put("Croatian", "hr");
        COUNTRY_CODE_MAP.put("Dutch", "nl");
        COUNTRY_CODE_MAP.put("Egyptian", "eg");
        COUNTRY_CODE_MAP.put("French", "fr");
        COUNTRY_CODE_MAP.put("Greek", "gr");
        COUNTRY_CODE_MAP.put("Indian", "in");
        COUNTRY_CODE_MAP.put("Irish", "ie");
        COUNTRY_CODE_MAP.put("Italian", "it");
        COUNTRY_CODE_MAP.put("Jamaican", "jm");
        COUNTRY_CODE_MAP.put("Japanese", "jp");
        COUNTRY_CODE_MAP.put("Kenyan", "ke");
        COUNTRY_CODE_MAP.put("Malaysian", "my");
        COUNTRY_CODE_MAP.put("Mexican", "mx");
        COUNTRY_CODE_MAP.put("Moroccan", "ma");
        COUNTRY_CODE_MAP.put("Polish", "pl");
        COUNTRY_CODE_MAP.put("Portuguese", "pt");
        COUNTRY_CODE_MAP.put("Russian", "ru");
        COUNTRY_CODE_MAP.put("Spanish", "es");
        COUNTRY_CODE_MAP.put("Thai", "th");
        COUNTRY_CODE_MAP.put("Tunisian", "tn");
        COUNTRY_CODE_MAP.put("Turkish", "tr");
        COUNTRY_CODE_MAP.put("Vietnamese", "vn");
    }



    public UniversalAdapter(Context context, List<Object> items, OnItemListener onItemListener) {
        this.context = context;
        this.items = new ArrayList<>(items);
        this.onItemListener = onItemListener;
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
            holder.itemView.setOnClickListener(v -> onItemListener.onItemClick(item));
            String imageUrl = "https://www.themealdb.com/images/ingredients/" +
                    ((Ingredient) item).getStrIngredient() + "-Small.png";
            holder.loadImage(imageUrl);
        } else if (item instanceof Category) {
            holder.itemName.setText(((Category) item).getCategoryName());
            holder.loadImage(((Category) item).getCategoryImage());
            holder.itemView.setOnClickListener(v -> onItemListener.onItemClick(item));
        } else if (item instanceof Country) {
            holder.itemName.setText(((Country) item).getCountryName());
            String countryCode = COUNTRY_CODE_MAP.getOrDefault(((Country) item).getCountryName(), "xx");
            String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";
            holder.loadImage(flagUrl);
            holder.itemView.setOnClickListener(v -> onItemListener.onItemClick(item));
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
