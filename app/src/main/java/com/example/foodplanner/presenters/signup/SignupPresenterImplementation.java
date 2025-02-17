package com.example.foodplanner.presenters.signup;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.views.ui.authentication.signup.SignUpView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupPresenterImplementation implements SignupPresenter {
    private final SignUpView signUpView;
    private final FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;

    public SignupPresenterImplementation(SignUpView signUpView,Context context) {
        this.signUpView = signUpView;
        this.firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences= context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void signUpWithEmail(String username, String email, String password, String confirmPassword) {
        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> updateUserProfile(username))
                .addOnFailureListener(e -> signUpView.onSignupFailure(e.getMessage()));
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty()) {
            signUpView.showValidationDialog("Username is required");
            return false;
        }
        if (email.isEmpty()) {
            signUpView.showValidationDialog("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            signUpView.showValidationDialog("Password is required");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            signUpView.showValidationDialog("Confirm Password is required");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            signUpView.showValidationDialog("Passwords do not match");
            return false;
        }
        return true;
    }

    private void updateUserProfile(String username) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        sharedPreferences.edit().putString("userName", username).apply();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnSuccessListener(unused -> signUpView.onSignupSuccess())
                    .addOnFailureListener(e -> signUpView.onSignupFailure("Failed to update profile: " + e.getMessage()));
        }
    }


}

