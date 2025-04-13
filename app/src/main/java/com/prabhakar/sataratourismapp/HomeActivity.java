package com.prabhakar.sataratourismapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment()); // default

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_about) {
                fragment = new AboutFragment();
            } else if (id == R.id.nav_map) {
                fragment = new MapFragment();
            } else if (id == R.id.nav_dashboard) {
                fragment = new DashboardFragment();
            } else {
                return false;
            }

            loadFragment(fragment);
            return true;
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

