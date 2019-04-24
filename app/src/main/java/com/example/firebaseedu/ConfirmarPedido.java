package com.example.firebaseedu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ConfirmarPedido extends AppCompatActivity implements View.OnClickListener {
    private int dia, mes, ano;
    private String f_entrega, f_recogida;
    EditText etPlannedDate,etDeadline, direccion_entrega;
    TextView total;
    Button pagar;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        ImageView cesto, volver;
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser Users =  firebaseAuth.getCurrentUser();

        // EditText etPlannedDate, etDeadline;
        volver = findViewById(R.id.cerrar);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmarPedido.this, home.class);
                startActivity(intent);
            }
        });
        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(this);


         etDeadline = (EditText) findViewById(R.id.etDeadline);
        etDeadline.setOnClickListener(this);

        pagar = findViewById(R.id.pagar);
        pagar.setOnClickListener(this);

        uid = Users.getUid();
        Log.d("aaaaaa", Servicios.productos.toString());
        direccion_entrega = findViewById(R.id.direc_entrega);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://limpi.mipantano.com/api/total",
                Servicios.productos, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("total", response.toString());


                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONArray objeto = response.getJSONArray(i);
                        Toast.makeText(ConfirmarPedido.this, "www "+objeto.get(i), Toast.LENGTH_SHORT).show();

//                        total.setText(obj.getString("precio"));


                        // adding movie to movies array


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                total = findViewById(R.id.total);
                total.setText("$"+response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Cesto", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etPlannedDate:
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        f_recogida = year + "-" + twoDigits(month+1) + "-" + twoDigits(dayOfMonth);
                        etPlannedDate.setText(f_recogida);
                    }
                },ano,mes,dia);
                datePickerDialog.show();

                break;

            case R.id.etDeadline:
                c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        f_entrega = year + "-" + twoDigits(month + 1) + "-" + twoDigits(dayOfMonth);
                        etDeadline.setText(f_entrega);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();

                break;
            case R.id.pagar:
            {
                JSONObject usuario = new JSONObject();
                JSONObject id = new JSONObject();
                JSONObject fecha_reco = new JSONObject();
                JSONObject fecha_entr = new JSONObject();
                JSONObject dire_entr = new JSONObject();
                try {
                    usuario.put("usuario", 1231);
                    id.put("uid",uid);
                    fecha_reco.put("f_recogida",f_recogida);
                    fecha_entr.put("f_entrega",f_entrega);
                    fecha_entr.put("direccion",direccion_entrega.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Servicios.productos.put(usuario);
                Servicios.productos.put(id);
                Servicios.productos.put(fecha_entr);
                Servicios.productos.put(fecha_reco);
                Servicios.productos.put(dire_entr);
                Toast.makeText(this, "> "+Servicios.productos, Toast.LENGTH_SHORT).show();
                //   cesto.setText(Servicios.productos.toString());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://limpi.mipantano.com/api/venta",
                        Servicios.productos, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Cesto", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Cesto", error.toString());
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(jsonArrayRequest);
            }
            break;
        }
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}
