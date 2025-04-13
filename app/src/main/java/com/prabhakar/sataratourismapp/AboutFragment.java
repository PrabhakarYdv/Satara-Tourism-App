package com.prabhakar.sataratourismapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        Button btnVisitWebsite = view.findViewById(R.id.btn_visit_website);
        ImageButton btnFacebook = view.findViewById(R.id.btn_facebook);
        ImageButton btnInstagram = view.findViewById(R.id.btn_instagram);
        ImageButton btnEmail = view.findViewById(R.id.btn_email);

        btnVisitWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("url", "https://www.satara.gov.in/");
            startActivity(intent);
        });

        btnFacebook.setOnClickListener(v -> openLink("https://www.facebook.com/SataraTourism"));
        btnInstagram.setOnClickListener(v -> openLink("https://www.instagram.com/sataratourism"));
        btnEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "contact@satara.gov.in", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Satara Tourism App");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        });

        return view;
    }

    private void openLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}