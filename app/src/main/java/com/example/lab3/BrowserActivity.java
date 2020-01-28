package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        try{
            String result = this.getIntent().getStringExtra("dataKey");
            WebView webView = findViewById(R.id.webView);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(result);
        }
        catch(Exception e){
            String result = this.getIntent().getDataString().toString();
            WebView webView = findViewById(R.id.webView);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(result);
        }

    }
}
