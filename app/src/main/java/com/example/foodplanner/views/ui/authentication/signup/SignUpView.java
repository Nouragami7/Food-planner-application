package com.example.foodplanner.views.ui.authentication.signup;

public interface SignUpView {
    void onSignupSuccess();
    void onSignupFailure(String errorMessage);
    void showValidationDialog(String message);
}
