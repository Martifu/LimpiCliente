package com.example.firebaseedu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {
    private Button btn1;
    private EditText correo;
    private EditText contra;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        btn1=  findViewById(R.id.btniniciar);
        correo= findViewById(R.id.editemail);
        contra=  findViewById(R.id.editTextpassword);

        firebaseAuth.getInstance().signOut();
        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
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
