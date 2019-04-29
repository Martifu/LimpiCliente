package com.example.firebaseedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebaseedu.Modelos.Membresia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MembresiaSeleccionada extends AppCompatActivity {
    Button pagar;
    String uid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membresia_seleccionada);
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser Users =  firebaseAuth.getCurrentUser();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final TextView precio, detalles, tipo, descripcion;
        final ImageView imagen, imagen_detalles, cerrar, cesto;
        precio = findViewById(R.id.precio);
        imagen = findViewById(R.id.imagen);
        detalles = findViewById(R.id.detalles);
        descripcion = findViewById(R.id.descripcion);
        imagen_detalles = findViewById(R.id.imagen_detalle);
        tipo = findViewById(R.id.plan);
        cerrar = findViewById(R.id.cerrar);
        cesto = findViewById(R.id.cesto);
        pagar = findViewById(R.id.btn_pagar_membre);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        Integer id = (Integer) my_bundle_received.get("id");
        JSONObject datos = new JSONObject();
        uid = Users.getUid();
        try {
            datos.put("id",id);
            datos.put("uid",uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONArray data = new JSONArray();
        data.put(id);
        data.put(uid);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://limpi.mipantano.com/api/membresia",
                data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i=0; i<response.length(); i++)
                    {
                        JSONObject object = response.getJSONObject(i);
                        Picasso.with(MembresiaSeleccionada.this).load(object.getString("imagen")).into(imagen);
                        Picasso.with(MembresiaSeleccionada.this).load(object.getString("imagen_detalles")).into(imagen_detalles);
                        precio.setText("$"+object.getString("Precio"));
                        tipo.setText(object.getString("tipo"));
                        detalles.setText(object.getString("detalles"));
                        descripcion.setText(object.getString("Descripcion"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MembresiaSeleccionada.this);
                progressDialog.setMessage("Cargando, por favor espere....");
                progressDialog.setTitle("Pago");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://limpi.mipantano.com/api/pagar_membresia",
                        data, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      Log.d("Membresia",response.toString());
                        progressDialog.dismiss();
                        Toast.makeText(MembresiaSeleccionada.this, "Membresia comprada con exito!!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(MembresiaSeleccionada.this);
                queue.add(jsonArrayRequest);
            }
        });
    }
}
