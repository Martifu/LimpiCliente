package com.example.firebaseedu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PedidoSeleccionado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_seleccionado);
        final Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        Integer id = (Integer) my_bundle_received.get("id");
        Toast.makeText(this, "folio "+id, Toast.LENGTH_SHORT).show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
