package com.example.foodplanner.presenters.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.views.ui.authentication.login.LogInView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImplementation implements LoginPresenter {

    private final LogInView loginView;
    private final FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;

    public LoginPresenterImplementation(LogInView loginView, Context context) {
        this.loginView = loginView;
        this.firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void login(String email, String password) {
        if (!validateInputs(email, password)) {
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser != null) {
                        loginView.onLoginSuccess();
                        sharedPreferences.edit().putString("userId", currentUser.getUid()).apply();
                        sharedPreferences.edit().putString("userName", currentUser.getDisplayName()).apply();
                        sharedPreferences.edit().putString("userEmail", currentUser.getEmail()).apply();
                    }
                })
                .addOnFailureListener(e -> loginView.onLoginFailure(e.getMessage()));
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            loginView.showValidationDialog("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            loginView.showValidationDialog("Password is required");
            return false;
        }
        if (password.length() < 6) {
            loginView.showValidationDialog("Password must be at least 6 characters");
            return false;
        }
        return true;
    }
}

