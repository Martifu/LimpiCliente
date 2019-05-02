package com.example.firebaseedu;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        Integer total = getIntent().getExtras().getInt("Total");
        String uid = getIntent().getExtras().getString("Uid");
        paypal.loadUrl("http://limpi.mipantano.com/paypal/"+total+"/"+uid);
        Log.d("tot",total.toString());
        Log.d("tot",uid);
        paypal.getSettings().setJavaScriptEnabled(true);
        paypal.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });






    }
}
