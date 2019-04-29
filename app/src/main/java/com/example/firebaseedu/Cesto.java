package com.example.firebaseedu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebaseedu.Adaptadores.AdaptadorCesto;
import com.example.firebaseedu.Adaptadores.AdaptadorTintoreria;
import com.example.firebaseedu.Modelos.TintoreriaProductos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

public class Cesto extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesto);
        ImageView cesto, volver;
        Button siguiente;
        // EditText etPlannedDate, etDeadline;
        volver = findViewById(R.id.cerrar);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cesto.this, home.class);
                startActivity(intent);
            }
        });
        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //   cesto = findViewById(R.id.micesto);




//        Toast.makeText(this, "cest " + Servicios.productos, Toast.LENGTH_SHORT).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://limpi.mipantano.com/api/cesto",
                Servicios.agregados, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Cesto", response.toString());
                RecyclerView.Adapter adapter;
                RecyclerView recyclerView;
                recyclerView = findViewById(R.id.rcv_cesto);
                Gson gson = new Gson();
                Type type = new TypeToken<List<com.example.firebaseedu.Modelos.Cesto>>() {
                }.getType();
                List<com.example.firebaseedu.Modelos.Cesto> lp = gson.fromJson(response.toString(), type);
                adapter = new AdaptadorCesto(lp, Cesto.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Cesto.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(new LinearLayoutManager(Cesto.this));
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(Cesto.this);
        queue.add(jsonArrayRequest);

        siguiente = findViewById(R.id.siguiente);
        siguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.siguiente:
            {
                startActivity(new Intent(Cesto.this,ConfirmarPedido.class));
                finish();
            }
            break;
        }
    }
}
