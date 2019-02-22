package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final int duracion = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //quitar orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ocultar barra



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iniciosesion = new Intent(MainActivity.this, InicioSesion.class);
                startActivity(iniciosesion);
                finish();
            }
        },duracion);
    }
}
