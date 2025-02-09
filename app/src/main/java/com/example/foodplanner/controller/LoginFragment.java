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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    TextView signupText;
    Button loginBtn;
    EditText emailEditText, passwordEditText;
    private FirebaseAuth myAuthantication;


    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupText = view.findViewById(R.id.signupTxt);
        loginBtn = view.findViewById(R.id.loginBtn);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        myAuthantication = FirebaseAuth.getInstance();

        signupText.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_signupFragment);
        });

        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login(email,password);
            Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_homeFragment);

        });

    }
    private void login(String email , String password){
        myAuthantication.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment2_to_homeFragment);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Login Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}