package com.example.foodplanner.views.ui.authentication.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.presenters.signup.SignupPresenterImplementation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignupFragment extends Fragment implements SignUpView {

    Button signupBtn;
    TextView loginText;

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    SignupPresenterImplementation signupPresenter;
    SharedPreferences sharedPreferences;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupBtn = view.findViewById(R.id.signupBtn);
        loginText = view.findViewById(R.id.loginTxt);
        loginText = view.findViewById(R.id.loginTxt);
        usernameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPassEditText);
        signupPresenter = new SignupPresenterImplementation(this);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        signupBtn.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            signupPresenter.signUpWithEmail(username, email, password, confirmPassword);
        });

        loginText.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment2)
        );

}

    @Override
    public void onSignupSuccess() {
        sharedPreferences.edit()
                .putString("userEmail", emailEditText.getText().toString().trim())
                .putString("userName", usernameEditText.getText().toString().trim())
                .putString("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .apply();
        Toast.makeText(getContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment2);

    }

    @Override
    public void onSignupFailure(String errorMessage) {
        Toast.makeText(getContext(), "SignUp Failed: " + errorMessage, Toast.LENGTH_SHORT).show();

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




