package com.example.foodplanner.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.foodplanner.R;

public class SplashFragment extends Fragment {
    TextView dish,dash;
    boolean logedIn = false;

    public SplashFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_splash, container, false);
        dish = view.findViewById(R.id.dish);
        dash= view.findViewById(R.id.dash);

        Animation slideInFromLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_left);
        Animation slideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_right);

        // Start the animations
        dash.startAnimation(slideInFromLeft);
        dish.startAnimation(slideInFromRight);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_introductionFragment2);
            }
        },3000);
    }
}