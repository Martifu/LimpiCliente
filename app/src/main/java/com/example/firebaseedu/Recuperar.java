package com.example.firebaseedu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperar extends AppCompatActivity {
    private EditText correo;
    private Button bt1, close;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_recuperar);
        correo=(EditText) findViewById(R.id.recovery_correo);
        bt1=(Button) findViewById(R.id.recovery_enviar);
        close=(Button) findViewById(R.id.recovery_cerrar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void send(View view) {
        String userEmail = correo.getText().toString();
        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(Recuperar.this,"Ingrese el correo",Toast.LENGTH_LONG).show();
        }
        else
        {

            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Recuperar.this,"el correo fue enviado exitosamente", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Recuperar.this,InicioSesion.class));
                    }
                    else
                    {
                        String mensaje= task.getException().getMessage();
                        Toast.makeText(Recuperar.this,"Error: "+mensaje,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void cerrar(View view) {

        Intent cerraer = new Intent(Recuperar.this,InicioSesion.class);
        startActivity(cerraer);
    }
}
