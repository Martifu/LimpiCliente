package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class InicioSesion extends AppCompatActivity {
    private Button btn1;
    ProgressBar p;
    private EditText correo;
    private EditText contra;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //componentes FACEBOOK
    LoginButton loginButton;
    CallbackManager callbackManager; //se encarga de recibir la respuesta de FB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        btn1=  findViewById(R.id.btniniciar);
        correo= findViewById(R.id.editemail);
        contra=  findViewById(R.id.editTextpassword);
        p=findViewById(R.id.progress);


        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //quitar orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Inicialización de sdk de FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        //LoginResult de FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(InicioSesion.this, "Se canceló", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(InicioSesion.this, "Hubo un error", Toast.LENGTH_SHORT).show();
            }
        });


        firebaseAuth.getInstance().signOut();
        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){

                    //metodo que lleva a activity home una vez se haya logeado
                    MainFacebook();
                    if(!user.isEmailVerified())
                        Toast.makeText(InicioSesion.this, "Favor de verificar su correo",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(InicioSesion.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(InicioSesion.this, "¡Welcome to Limpi!",Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    //verificacion de token con FACEBOOK y firebase
    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( !task.isSuccessful() )
                        {
                            Toast.makeText(getApplicationContext(),R.string.firebase_error_login,Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }

    //metodo donde te manda a activity home despues de iniciar sesion con FACEBOOK
    private void MainFacebook() {
        Intent intent = new Intent(InicioSesion.this, home.class);
        startActivity(intent);
        finish();
    }

//metodo que recibe las respuestas del loggeo con FACEBOOK
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void registrarusuario(View view) {
    }

    public void sesionar(View view) {
        String email = correo.getText().toString();
        String pass = contra.getText().toString();
        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
            Toast.makeText(InicioSesion.this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();

        }
        else
        {

            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        String mensaje= task.getException().getMessage();
                        Toast.makeText(InicioSesion.this,"Error: "+mensaje,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        startActivity(new Intent(InicioSesion.this,MainActivity.class));
                    }
                }
            });
        }

    }

    public void recuperapass(View view) {
    }

















    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
