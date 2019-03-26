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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class registro_usuarios extends AppCompatActivity {

    private EditText usernameText;
    private EditText passText;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
     EditText nom, apell, vcontra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);
        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        usernameText=(EditText) findViewById(R.id.editemail);
        passText=(EditText) findViewById(R.id.editpass);
        nom = findViewById(R.id.editnombre);
        apell = findViewById(R.id.editapellido);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser Users =  firebaseAuth.getCurrentUser();
                if (Users != null){
                    Toast.makeText(registro_usuarios.this,"ya se creo el usuario correctamente ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(registro_usuarios.this,"no se no fue creado",Toast.LENGTH_LONG).show();
                }
            }
        };

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

    public void registrar(View view) {
        String username = usernameText.getText().toString();
        String passwor = passText.getText().toString();
        String nombre = nom.getText().toString();
        String apellido = apell.getText().toString();

        JSONObject datos = new JSONObject();
        try {
            datos.put("nombre",nombre);
            datos.put("apellido",apellido);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, "http://limpi.mipantano.com/usuario",
                datos, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(registro_usuarios.this, "Registrado exitosamente!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyS.getInstance(this).getRequestQueue().add(objectRequest);

        firebaseAuth.createUserWithEmailAndPassword(username,passwor).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(registro_usuarios.this,"hubo un error", Toast.LENGTH_LONG).show();
                }
                else{
                    FirebaseUser Users =  firebaseAuth.getCurrentUser();
                    Users.sendEmailVerification();
                    Intent intent = new Intent(registro_usuarios.this,home.class);
                    startActivity(intent);
                }
            }
        });
    }

}
