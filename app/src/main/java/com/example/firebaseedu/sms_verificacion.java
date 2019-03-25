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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class sms_verificacion extends AppCompatActivity  {



    EditText edtnumero, edtcodigo;
    Button btnnumero,btncodigo;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String verificarid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verificacion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        edtcodigo=findViewById(R.id.edtcodigo);
        edtnumero=findViewById(R.id.edtnumero);

        btnnumero=findViewById(R.id.btnnumero);
        btncodigo=findViewById(R.id.btncodigo);


        btnnumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numero = edtnumero.getText().toString();
                if ( TextUtils.isEmpty(numero) )
                    return;

                PhoneAuthProvider.getInstance().verifyPhoneNumber(

                        numero, 60, TimeUnit.SECONDS, sms_verificacion.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                signInWithCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                                Toast.makeText(sms_verificacion.this, "Falló la verificacion", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(String verificarID, PhoneAuthProvider.ForceResendingToken idToken) {
                                super.onCodeSent(verificarID,idToken);
                                verificarid=verificarID;
                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(String verificarID) {
                                super.onCodeAutoRetrievalTimeOut(verificarID);
                                Toast.makeText(sms_verificacion.this, "Tiempo agotado", Toast.LENGTH_SHORT).show();
                            }
                        }


                );
            }
        });
        btncodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = edtcodigo.getText().toString();
                if ( TextUtils.isEmpty(code) )
                    return;
                signInWithCredential(PhoneAuthProvider.getCredential(verificarid,code));

            }
        });

        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){

                    Toast.makeText(sms_verificacion.this, "Ya está loggeado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(sms_verificacion.this,home.class);
                    startActivity(intent);

                }
            }
        };

    }



    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if ( task.isSuccessful() ) {
                        Toast.makeText(sms_verificacion.this, "Exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(sms_verificacion.this, "Falló", Toast.LENGTH_SHORT).show();
                    }
            }

        });
    }


}
