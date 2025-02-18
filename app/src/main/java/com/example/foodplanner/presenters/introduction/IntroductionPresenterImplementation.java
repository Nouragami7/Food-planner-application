package com.example.foodplanner.presenters.introduction;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.views.ui.introduction.IntroductionView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class IntroductionPresenterImplementation implements IntroductionPresenter {
    private final IntroductionView view;
    private final FirebaseAuth myAuthentication;
    SharedPreferences sharedPreferences;

    public IntroductionPresenterImplementation(IntroductionView view, Context context) {
        this.view = view;
        myAuthentication = FirebaseAuth.getInstance();
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        myAuthentication.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = myAuthentication.getCurrentUser();
                    if (user != null) {
                        sharedPreferences.edit().putString("userId", user.getUid()).apply();
                        sharedPreferences.edit().putString("userEmail", user.getEmail()).apply();
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
                        sharedPreferences.edit().putString("userName", user.getDisplayName()).apply();
                        view.showSuccess("Signup Successful");
                    }
                })
                .addOnFailureListener(e -> view.showError("Signup Failed: " + e.getMessage()));
    }
}
