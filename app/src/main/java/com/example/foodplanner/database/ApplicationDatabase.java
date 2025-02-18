package com.example.foodplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.models.database.MealStorage;
import com.example.foodplanner.utilts.Converter;


@Database(entities = {MealStorage.class}, version = 2)
@TypeConverters(Converter.class)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static ApplicationDatabase database = null;

    public abstract MealDAO mealDAO();

    public static synchronized ApplicationDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                    ApplicationDatabase.class, "meals_database")
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }
}
