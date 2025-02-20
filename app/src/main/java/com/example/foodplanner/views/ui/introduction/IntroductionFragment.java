package com.example.foodplanner.views.ui.introduction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.presenters.introduction.IntroductionPresenter;
import com.example.foodplanner.presenters.introduction.IntroductionPresenterImplementation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;


public class IntroductionFragment extends Fragment implements IntroductionView {

    Button singupWithEmailBtn;
    Button singupWithGoogleBtn;
    TextView login;
    private GoogleSignInClient mGoogleSignInClient;
    Button guestBtn;
    private IntroductionPresenter presenter;
    SharedPreferences sharedPreferences;

    private static final String TAG = "IntroductionFragment";

    public IntroductionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

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
        guestBtn = view.findViewById(R.id.skipBtn);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new IntroductionPresenterImplementation(this, requireContext());
        singupWithEmailBtn.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_signupFragment);
        });

        login.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_loginFragment2);
        });
        singupWithGoogleBtn.setOnClickListener(v -> {
            signInWithGoogle();
        });
        guestBtn.setOnClickListener(v -> {
            sharedPreferences.edit().putString("userId", "guest").apply();
            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_homeFragment);
        });

    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.signInWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "LogIn Failed", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void showSuccess(String message) {
        showSnackBar(message, getView());
        Navigation.findNavController(getView()).navigate(R.id.action_introductionFragment2_to_homeFragment);

    }

    @Override
    public void showError(String message) {
        showSnackBar(message, getView());
    }

    private void showSnackBar(String message, View fragmentView) {
        int ColorResId = R.color.light_pink;
        int color = ContextCompat.getColor(requireContext(), ColorResId);
        Snackbar snackbar = Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}