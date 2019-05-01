package com.example.firebaseedu;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Paypal extends AppCompatActivity {

    WebView paypal;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        paypal = findViewById(R.id.pay);

        String total = getIntent().getExtras().getString("Total");
        String uid = getIntent().getExtras().getString("Uid");

        paypal.getSettings().setJavaScriptEnabled(true);

        paypal.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        paypal.loadUrl("http://limpi.mipantano.com/paypal/"+total+"/"+uid);




    }
}
