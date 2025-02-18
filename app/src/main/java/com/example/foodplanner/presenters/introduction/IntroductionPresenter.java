package com.example.foodplanner.presenters.introduction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface IntroductionPresenter {
    void signInWithGoogle(GoogleSignInAccount account);
}
