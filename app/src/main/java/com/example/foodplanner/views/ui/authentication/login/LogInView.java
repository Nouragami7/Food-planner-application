package com.example.foodplanner.views.ui.authentication.login;

public interface LogInView {
    void onLoginSuccess();
    void onLoginFailure(String errorMessage);
    void showValidationDialog(String message);
}
