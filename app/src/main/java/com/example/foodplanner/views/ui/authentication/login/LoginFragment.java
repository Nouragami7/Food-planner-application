package com.example.foodplanner.views.ui.authentication.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.presenters.login.LoginPresenterImplementation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginFragment extends Fragment implements LogInView {
    TextView signupText;
    Button loginBtn;
    EditText emailEditText, passwordEditText;
    SharedPreferences sharedPreferences;
    LoginPresenterImplementation loginPresenter;

    String userId;



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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupText = view.findViewById(R.id.signupTxt);
        loginBtn = view.findViewById(R.id.loginBtn);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loginPresenter = new LoginPresenterImplementation(this, getContext());

        signupText.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_signupFragment)
        );

        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginPresenter.login(email, password);
        });
    }

    @Override
    public void onLoginSuccess() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment2_to_homeFragment);

    }

    @Override
    public void onLoginFailure(String errorMessage) {
        Toast.makeText(getContext(), "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidationDialog(String message) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button dialogButton = dialogView.findViewById(R.id.dialogButton);

        dialogMessage.setText(message);
        dialogButton.setOnClickListener(v -> alertDialog.dismiss());

    }
}
