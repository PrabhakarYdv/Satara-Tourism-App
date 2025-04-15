package com.prabhakar.sataratourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtSignupEmail, edtSignupPassword;
    private Button btnSignup;
    private TextView txtAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        btnSignup = findViewById(R.id.btnSignup);
        txtAlreadyAccount = findViewById(R.id.txtAlreadyAccount);

        btnSignup.setOnClickListener(v -> registerUser());

        txtAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String email = edtSignupEmail.getText().toString().trim();
        String password = edtSignupPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtSignupEmail.setError("Enter a valid email");
            edtSignupEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtSignupPassword.setError("Password must be at least 6 characters");
            edtSignupPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(SignupActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignupActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}