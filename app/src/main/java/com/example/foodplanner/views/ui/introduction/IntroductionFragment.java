package com.example.foodplanner.views.ui.introduction;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.presenters.introduction.IntroductionPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class IntroductionFragment extends Fragment implements IntroductionView {

    Button singupWithEmailBtn;
    Button singupWithGoogleBtn;
    TextView login;
    private GoogleSignInClient mGoogleSignInClient;
    private IntroductionPresenter presenter;

   private static final String TAG ="IntroductionFragment";

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
        singupWithEmailBtn.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_signupFragment);
        });

        login.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_introductionFragment2_to_loginFragment2);
        });
        singupWithGoogleBtn.setOnClickListener(v -> {
           signInWithGoogle();
        });

    }

    private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
               presenter.signInWithGoogle(account);
            }catch (ApiException e){
                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(getView()).navigate(R.id.action_introductionFragment2_to_homeFragment);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}