package com.example.foodplanner.views.adapters;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.models.DTOS.Country;
import com.example.foodplanner.views.ui.meal_filtering.OnCountryClickListener;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {
    private Context context;
    ArrayList<Country> countries;
    private OnCountryClickListener countryClickListener;

    public CountryAdapter(Context context, ArrayList<Country> countries,OnCountryClickListener onCountryClickListener) {
        this.context = context;
        this.countries = countries;
        this.countryClickListener =  onCountryClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.country_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country country = countries.get(position);
        Log.i("TAG", "onBindViewHolder: "+ country.getCountryName());
        if (country != null && country.getCountryName() != null) {
            String countryCode = getCountryCode(country.getCountryName());
            String flagUrl = "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";
            holder.countryName.setText(country.getCountryName());
            Glide.with(context)
                    .load(flagUrl)
                    .into(holder.countryImage);
        }
        holder.itemView.setOnClickListener(v -> {
            countryClickListener.onCountryClick(countries.get(position).getCountryName());
        });
    }

    @Override
    public int getItemCount() {
        return (countries != null) ? countries.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView countryImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryName);
            countryImage = itemView.findViewById(R.id.countryImage); // Ensure this ID matches XML
        }
    }

    private String getCountryCode(String countryName) {
        if (countryName == null) return "xx";

        countryName = countryName.toLowerCase();

        if (countryName.equals("american")) return "us";
        else if (countryName.equals("british")) return "gb";
        else if (countryName.equals("canadian")) return "ca";
        else if (countryName.equals("chinese")) return "cn";
        else if (countryName.equals("croatian")) return "hr";
        else if (countryName.equals("dutch")) return "nl";
        else if (countryName.equals("egyptian")) return "eg";
        else if (countryName.equals("french")) return "fr";
        else if (countryName.equals("greek")) return "gr";
        else if (countryName.equals("indian")) return "in";
        else if (countryName.equals("irish")) return "ie";
        else if (countryName.equals("italian")) return "it";
        else if (countryName.equals("jamaican")) return "jm";
        else if (countryName.equals("japanese")) return "jp";
        else if (countryName.equals("kenyan")) return "ke";
        else if (countryName.equals("malaysian")) return "my";
        else if (countryName.equals("mexican")) return "mx";
        else if (countryName.equals("moroccan")) return "ma";
        else if (countryName.equals("polish")) return "pl";
        else if (countryName.equals("portuguese")) return "pt";
        else if (countryName.equals("russian")) return "ru";
        else if (countryName.equals("spanish")) return "es";
        else if (countryName.equals("thai")) return "th";
        else if (countryName.equals("tunisian")) return "tn";
        else if (countryName.equals("turkish")) return "tr";
        else if (countryName.equals("vietnamese")) return "vn";
        else return "x";
    }
}
