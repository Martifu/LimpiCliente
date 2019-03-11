package com.example.firebaseedu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    EditaPerfil.OnFragmentInteractionListener, MetodosDePago.OnFragmentInteractionListener
{
    String uri_parse;
    TextView usuario;
    View btn;
    ActionBarDrawerToggle mToggle;
    DrawerLayout mDrawerLayout;

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        //recibir datos
        String name = getIntent().getStringExtra("nombre");
        uri_parse=getIntent().getStringExtra("uriFoto");

        TextView user_name = (TextView) findViewById(R.id.user);
        user_name.setText(name);




        //quitar orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ocultar barra
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

            mDrawerLayout=findViewById(R.id.drawer);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            btn=findViewById(R.id.btn);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    private void goBack() {
        Intent intent = new Intent(home.this,InicioSesion.class);
        startActivity(intent);

        finish();
    }
    public void logOut(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goBack();
    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
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
