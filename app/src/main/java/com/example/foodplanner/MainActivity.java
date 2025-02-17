package com.example.foodplanner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.interfacies.NetworkStateListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private NavController navController;
    private LottieAnimationView lottieAnimationView;
    private FragmentContainerView fragmentContainerView;

    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    NetworkStateListener networkStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        lottieAnimationView = findViewById(R.id.errorAnimationView);
        fragmentContainerView = findViewById(R.id.nav_host_fragment);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleBottomNavigation(item);
            }
        });

        Set<Integer> navButtonVisibility = new HashSet<>(Arrays.asList(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.favouriteFragment,
                R.id.planFragment
        ));

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (navButtonVisibility.contains(destination.getId())) {
                bottomNav.setVisibility(View.VISIBLE);
            } else {
                bottomNav.setVisibility(View.GONE);
            }
            if (destination.getId() == R.id.favouriteFragment || destination.getId() == R.id.planFragment) {
                lottieAnimationView.setVisibility(View.GONE);
                fragmentContainerView.setVisibility(View.VISIBLE);
            } else {
                updateUIBasedOnNetwork();
            }
        });

        registerNetworkCallback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private boolean handleBottomNavigation(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.homeFragment) {
            navController.navigate(R.id.homeFragment);
            return true;
        } else if (itemId == R.id.favouriteFragment) {
            navController.navigate(R.id.favouriteFragment);
            return true;
        } else if (itemId == R.id.planFragment) {
            navController.navigate(R.id.planFragment);
            return true;
        } else if (itemId == R.id.searchFragment) {
            navController.navigate(R.id.searchFragment);
            return true;
        }
        return false;
    }

    private void registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() -> {
                    // Network is available
                    updateUIBasedOnNetwork();
                    if (networkStateListener != null) networkStateListener.onNetworkAvailable();
                    showErrorSnackBar("Network is available", R.color.green);
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() -> {
                    // Network is lost
                    updateUIBasedOnNetwork();
                    if (networkStateListener != null) networkStateListener.onNetworkLost();
                    showErrorSnackBar("Network is not available", R.color.red); // Show error message
                });
            }
        };


        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    private void updateUIBasedOnNetwork() {
        if (isNetworkAvailable()) {
            lottieAnimationView.setVisibility(View.GONE);
            fragmentContainerView.setVisibility(View.VISIBLE);
        } else {
            lottieAnimationView.setVisibility(View.VISIBLE);
            fragmentContainerView.setVisibility(View.GONE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    private void showErrorSnackBar(String message, int colorResId) {
        int color = ContextCompat.getColor(this, colorResId);
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void setNetworkStateListener(NetworkStateListener listener) {
        this.networkStateListener = listener;
    }
}