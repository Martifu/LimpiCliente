package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Objects;

public class InicioSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{
    private Button btn1;
    FloatingActionButton fb, main,google,btnsms;
    Float translationY=100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();

    private EditText correo;
    private EditText contra;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    Boolean menuOpen=false;

    //Componente GOOGLE
    GoogleApiClient mGoogleApiClient;

    public static final int SIGN_CODE=777;
    //componentes FACEBOOK

    CallbackManager callbackManager; //se encarga de recibir la respuesta de FB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Inicialización de sdk de FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        btn1=  findViewById(R.id.btniniciar);
        correo= findViewById(R.id.editemail);
        contra=  findViewById(R.id.editTextpassword);
    //    btnsms=findViewById(R.id.btnsms);
  //      btnsms.setOnClickListener(this);




        callbackManager = CallbackManager.Factory.create();
        main = findViewById(R.id.options);
        fb=findViewById(R.id.login_button);
        google = findViewById(R.id.google);
        btnsms = findViewById(R.id.sms);

        fb.setAlpha(0f);
        google.setAlpha(0f);
        btnsms.setAlpha(0f);

        main.setTranslationY(translationY);
        fb.setTranslationY(translationY);
        google.setTranslationY(translationY);
        btnsms.setTranslationY(translationY);

        main.setOnClickListener(this);
        fb.setOnClickListener(this);
        btnsms.setOnClickListener(this);
        google.setOnClickListener(this);
        //quitar orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Creea SingIn Google opciones
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_id__client))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(InicioSesion.this).enableAutoManage(this,  this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        //GOOGLE


//        firebaseAuth.getInstance().signOut();
        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){

                    //metodo que lleva a activity home una vez se haya logeado


                    SingGoogle();
                    MainFacebook();
                    startActivity(new Intent(InicioSesion.this, home.class));
                }
                else
                {
                    Toast.makeText(InicioSesion.this, "¡Bienvenido a   Limpi!",Toast.LENGTH_LONG).show();

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

      /* Profile perfil =  com.facebook.Profile.getCurrentProfile();
        String nombre = perfil.getName();
        Uri uriFoto = perfil.getProfilePictureUri(30,30);*/
        Intent intent = new Intent(InicioSesion.this, home.class);
      /*  intent.putExtra("nombre",nombre);
        intent.putExtra("uriFoto",uriFoto.toString());*/
        startActivity(intent);
        finish();
    }

//metodo que recibe las respuestas del loggeo con FACEBOOK
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //Checa respuesta de logeo de Google
        if (requestCode == SIGN_CODE){
            GoogleSignInResult resultado = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(resultado);
        }
    }


    public void registrarusuario(View view) {
        Intent inte = new Intent(this , registro_usuarios.class);
        startActivity(inte);
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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {

                            // Check if user's email is verified
                            boolean emailVerified = user.isEmailVerified();
                            if(!user.isEmailVerified()) {
                                Toast.makeText(InicioSesion.this, "Favor de verificar su correo", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }
            });
        }

    }

    public void recuperapass(View view) {
        Intent inte = new Intent(this, Recuperar.class);
        startActivity(inte);
    }

    //METODOS GOOGLE

    private void SingGoogle(){
        Intent intent = new Intent(InicioSesion.this,home.class);
        startActivity(intent);

    }

    private void handleSignInResult(GoogleSignInResult result){
        if (result.isSuccess()){
            //GoogleSignInAccount cuenta = resultado.getSignInAccount();
            firebaseAuthWithGoogle(result.getSignInAccount());
            //Log.d("Usuario", cuenta.getDisplayName());
        }
        else {
            Toast.makeText(this, "Error de inicio de sesion con google", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( !task.isSuccessful() )

                {
                    Toast.makeText(InicioSesion.this, "No se pudo autenticar ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void Openmenu(){
        menuOpen=!menuOpen;

        main.animate().setInterpolator(interpolator).rotationBy(45f).setDuration(300).start();
        fb.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        google.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        btnsms.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void Closemenu(){
        menuOpen=!menuOpen;
        main.animate().setInterpolator(interpolator).rotationBy(45f ).setDuration(300).start();
        fb.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        google.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        btnsms.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }
    @Override
    public void onClick(View v) {
        /*Intent sms = new Intent(InicioSesion.this,sms_verificacion.class);
        startActivity(sms);*/


        switch (v.getId()){
            case R.id.options:
                    if ( !menuOpen ){
                        Openmenu();
                    }
                    else {
                        Closemenu();
                    }
                break;
            case R.id.google:
                Intent intent =  Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent,SIGN_CODE);
                break;
            case R.id.login_button:
                //LoginResult de FACEBOOK
                fb = findViewById(R.id.login_button);
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookToken(loginResult.getAccessToken());

                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(InicioSesion.this, "Inicio Cancelado", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(InicioSesion.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginManager.getInstance().logInWithReadPermissions(InicioSesion.this, Arrays.asList("public_profile", "user_friends","email"));
                    }
                });

                break;
            case R.id.sms:
                Intent intento = new Intent(InicioSesion.this,sms_verificacion.class);
                startActivity(intento);
                break;
        }

        }
}
