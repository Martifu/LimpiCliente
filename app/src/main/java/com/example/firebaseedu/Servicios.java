package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebaseedu.Modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Servicios extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        EditaPerfil.OnFragmentInteractionListener, MetodosDePago.OnFragmentInteractionListener, Membresias.OnFragmentInteractionListener, PlanchadoFragment.OnFragmentInteractionListener,
        LavanderiaFragment.OnFragmentInteractionListener, TintoreriaFragment.OnFragmentInteractionListener, HogarFragment.OnFragmentInteractionListener {

    DrawerLayout mDrawerLayout;
    Button btn;
    ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser Users =  firebaseAuth.getCurrentUser();
        try {
            final String uid = Users.getUid();
            JSONObject datos = new JSONObject();
            datos.put("uid",uid);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://limpi.mipantano.com/api/usuario_info",
                    datos, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    TextView usuario;
                    Gson gson = new Gson();
                    Usuario json = gson.fromJson(response.toString(),Usuario.class);
                   usuario = findViewById(R.id.usuarioS);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error",error.toString());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(Servicios.this);
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //quitar orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mDrawerLayout=findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        btn=findViewById(R.id.btn_menu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

            }
        });

        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this);

        //NavBarBottom



        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.planchado:
                                selectedFragment = new  PlanchadoFragment();
                                break;
                            case R.id.lavanderia:
                                selectedFragment = new  LavanderiaFragment();
                                break;
                            case R.id.tintoreria:
                                selectedFragment = new  TintoreriaFragment();
                                break;
                            case R.id.hogar:
                                selectedFragment = new  HogarFragment();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new LavanderiaFragment());
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();


        Fragment fragment = null;
        Boolean FragmentSelected=false;

        if ( id==R.id.inicio)
        {
            fragment = new EditaPerfil();
            FragmentSelected=true;
        }
        else if(id==R.id.pagos)
        {
            fragment = new MetodosDePago();
            FragmentSelected=true;
        }
        else if (id == R.id.Membresias)
        {
            fragment = new Membresias();
            FragmentSelected=true;
        }
        else if (id == R.id.pagos)
        {
            fragment = new MetodosDePago();
            FragmentSelected=true;
        }
        else if (id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Servicios.this, InicioSesion.class));
        }
        if ( FragmentSelected ){

            getSupportFragmentManager().beginTransaction().replace(R.id.relative,fragment).commit();
            menuItem.setChecked(true);
        }

        mDrawerLayout=findViewById(R.id.drawer);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
