package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private final int duracion = 3000;
    private FirebaseAuth.AuthStateListener authStateListener;

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

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser Users =  firebaseAuth.getCurrentUser();
                if (Users != null){
                    Intent iniciosesion = new Intent(MainActivity.this, home.class);
                    startActivity(iniciosesion);
                }else{
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
        };



    }
}
