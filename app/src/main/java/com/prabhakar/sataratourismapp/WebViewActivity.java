package com.prabhakar.sataratourismapp;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}