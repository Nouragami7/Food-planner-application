package com.example.foodplanner.presenters.introduction;

import com.example.foodplanner.views.ui.introduction.IntroductionView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class IntroductionPresenterImplementation implements IntroductionPresenter {
    private IntroductionView view;
    private FirebaseAuth myAuthentication;


    public IntroductionPresenterImplementation(IntroductionView view) {
        this.view = view;
        myAuthentication = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        myAuthentication.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> view.showSuccess("Login Successful"))
                .addOnFailureListener(e -> view.showError("Login Failed: " + e.getMessage()));
    }
}


