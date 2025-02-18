package com.example.foodplanner.utilts;

import androidx.room.TypeConverter;

import com.example.foodplanner.models.DTOS.Meal;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Date;

public class Converter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMeal(Meal meal) {
        return meal == null ? null : gson.toJson(meal);
    }
    @TypeConverter
    public static Meal toMeal(String mealJson) {
        if (mealJson == null) return null;
        Type type = new TypeToken<Meal>() {}.getType();
        return gson.fromJson(mealJson, type);
    }

}
