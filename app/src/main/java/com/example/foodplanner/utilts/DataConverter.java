package com.example.foodplanner.utilts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConverter {
    public static String getFormattedDate(String olddate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = inputFormat.parse(olddate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";

        }
    }
}