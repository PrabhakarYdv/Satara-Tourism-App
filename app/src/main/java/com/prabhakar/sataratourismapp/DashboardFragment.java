package com.prabhakar.sataratourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;


public class DashboardFragment extends Fragment {


    private TextView userName;
    private ImageView userPhoto;
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        userName = view.findViewById(R.id.userName);
        userPhoto = view.findViewById(R.id.userPhoto);
        logoutButton = view.findViewById(R.id.logoutButton);

        firebaseAuth = FirebaseAuth.getInstance();

        // Fetch user info
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            userName.setText(account.getDisplayName());

            // Load photo using Glide
            Glide.with(this)
                    .load(account.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_placeholder)
                    .into(userPhoto);
        }

        // Logout button click
        logoutButton.setOnClickListener(v -> {
            if (firebaseAuth.getCurrentUser()!=null){
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();
            }

//            googleSignInClient.signOut().addOnCompleteListener(task -> {
//                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
//
//            });
        });

        // Animate whole layout fade-in
        fadeInView(view);


        return view;
    }

    private void fadeInView(View view) {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(800);
        view.startAnimation(fadeIn);
    }
}