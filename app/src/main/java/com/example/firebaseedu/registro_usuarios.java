package com.example.firebaseedu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

                }else{

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
        final String username = usernameText.getText().toString();
        final String passwor = passText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(username,passwor).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(registro_usuarios.this,"Hubo un error", Toast.LENGTH_LONG).show();
                    Log.d("usuairo",username);
                    Log.d("pass",passwor);
                }
                else{
                    FirebaseUser Users =  firebaseAuth.getCurrentUser();
                    try {

                        String nombre = nom.getText().toString();
                        String apellido = apell.getText().toString();
                        String uid = Users.getUid();
                        String correo = Users.getEmail();
                        JSONObject datos = new JSONObject();
                        datos.put("nombre",nombre);
                        datos.put("apellido",apellido);
                        datos.put("uid",uid);
                        datos.put("correo",correo);
                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, "http://limpi.mipantano.com/api/usuario",
                                datos, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(registro_usuarios.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error",error.toString());
                            }
                        });
                        VolleyS.getInstance(registro_usuarios.this).getRequestQueue().add(objectRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    Users.sendEmailVerification();
                    Intent intent = new Intent(registro_usuarios.this,home.class);
                    startActivity(intent);
                }
            }
        });
    }

}
