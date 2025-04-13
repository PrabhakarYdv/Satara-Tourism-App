package com.prabhakar.sataratourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ImageView logo;
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.app_name);

        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logo.startAnimation(logoAnim);
        appName.startAnimation(fadeIn);

        firebaseAuth = FirebaseAuth.getInstance();




        new Handler().postDelayed(() -> {
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }, 2000);

    }
}