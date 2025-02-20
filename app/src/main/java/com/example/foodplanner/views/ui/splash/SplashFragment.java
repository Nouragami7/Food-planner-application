package com.example.foodplanner.views.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;

public class SplashFragment extends Fragment {
    TextView dish, dash;
    SharedPreferences sharedPreferences;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        dish = view.findViewById(R.id.dish);
        dash = view.findViewById(R.id.dash);

        Animation slideInFromLeft = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_left);
        Animation slideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_right);

        dash.startAnimation(slideInFromLeft);
        dish.startAnimation(slideInFromRight);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_introductionFragment2);
                }
            }
        }, 3000);
    }

}