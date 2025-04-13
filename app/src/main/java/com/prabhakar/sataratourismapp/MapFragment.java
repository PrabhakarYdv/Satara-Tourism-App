package com.prabhakar.sataratourismapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    private WebView webView;
    private Spinner locationSpinner;
    private Button goButton;
    private ProgressBar mapLoadingProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        webView = view.findViewById(R.id.osmWebView);
        locationSpinner = view.findViewById(R.id.locationSpinner);
        goButton = view.findViewById(R.id.goButton);
        mapLoadingProgress = view.findViewById(R.id.mapLoadingProgress);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Show progressbar while loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide loader after map loaded
                mapLoadingProgress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

        String mapHtml = "<html><head>" +
                "<meta name='viewport' content='initial-scale=1.0'>" +
                "<link rel='stylesheet' href='https://unpkg.com/leaflet/dist/leaflet.css'/>" +
                "<script src='https://unpkg.com/leaflet/dist/leaflet.js'></script>" +
                "<style> html, body, #map { height: 100%; margin: 0; padding: 0; } </style>" +
                "</head><body>" +
                "<div id='map'></div>" +
                "<script>" +
                "var map = L.map('map').setView([17.6847, 73.9934], 12);" +
                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { attribution: '&copy; OpenStreetMap contributors' }).addTo(map);" +

                // Markers
                "L.marker([17.6835, 73.9940]).addTo(map).bindPopup('Ajinkyatara Fort');" +
                "L.marker([17.7126, 73.8036]).addTo(map).bindPopup('Kaas Plateau');" +
                "L.marker([17.5936, 73.8041]).addTo(map).bindPopup('Thoseghar Waterfall');" +
                "L.marker([17.6592, 73.8644]).addTo(map).bindPopup('Sajjangad Fort');" +
                "L.marker([17.7078, 73.7980]).addTo(map).bindPopup('Kaas Lake');" +
                "L.marker([17.6765, 73.9638]).addTo(map).bindPopup('Yawateshwar Temple');" +
                "L.marker([17.6866, 73.9952]).addTo(map).bindPopup('Char Bhinti');" +
                "L.marker([17.7094, 73.7982]).addTo(map).bindPopup('Kas Pathar Valley');" +
                "L.marker([17.7064, 73.8014]).addTo(map).bindPopup('Kaas Wildlife Sanctuary');" +
                "L.marker([17.6847, 73.9934]).addTo(map).bindPopup('Satara City Center');" +

                // Move to location function
                "function moveTo(lat, lng) {" +
                "  map.setView([lat, lng], 14);" +
                "}" +
                "</script></body></html>";

        webView.loadDataWithBaseURL(null, mapHtml, "text/html", "UTF-8", null);

        goButton.setOnClickListener(v -> {
            int pos = locationSpinner.getSelectedItemPosition();
            double[][] coords = {
                    {17.6835, 73.9940}, // Ajinkyatara Fort
                    {17.7126, 73.8036}, // Kaas Plateau
                    {17.5936, 73.8041}, // Thoseghar Waterfall
                    {17.6592, 73.8644}, // Sajjangad Fort
                    {17.7078, 73.7980}, // Kaas Lake
                    {17.6765, 73.9638}, // Yawateshwar Temple
                    {17.6866, 73.9952}, // Char Bhinti
                    {17.7094, 73.7982}, // Kas Pathar Valley
                    {17.7064, 73.8014}, // Kaas Wildlife Sanctuary
                    {17.6847, 73.9934}  // Satara City Center
            };
            double lat = coords[pos][0];
            double lng = coords[pos][1];
            String js = "javascript:moveTo(" + lat + ", " + lng + ")";
            webView.evaluateJavascript(js, null);
        });

        return view;
    }
}