package com.example.foodplanner.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.R;


public class IntroductionFragment extends Fragment {

    Button singupWithEmailBtn;
    Button singupWithGoogleBtn;

    TextView login;

   private static final String TAG ="IntroductionFragment";

    public IntroductionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singupWithEmailBtn = view.findViewById(R.id.signupWithEmail);
        singupWithGoogleBtn = view.findViewById(R.id.singupWithGoogle);
        login = view.findViewById(R.id.loginTxt);

        singupWithEmailBtn.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_signupFragment);
        });

        login.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_loginFragment2);
        });

    }
}