package com.example.firebaseedu.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.firebaseedu.MembresiaSeleccionada;
import com.example.firebaseedu.Modelos.Membresia;
import com.example.firebaseedu.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorMembresias extends RecyclerView.Adapter<AdaptadorMembresias.ViewHolder> {
    List<Membresia> lp;
    Context c;

    public AdaptadorMembresias(List<Membresia> lp, Context c) {
        this.lp = lp;
        this.c = c;
    }

    @NonNull
    @Override
    public AdaptadorMembresias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.membresia_design,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorMembresias.ViewHolder viewHolder, final int i) {

                    Picasso.with(c).load(lp.get(i).getImagen()).into(viewHolder.imagen);
                    viewHolder.tipo.setText(lp.get(i).getTipo());
                    viewHolder.descripcion.setText(lp.get(i).getDescripcion());
                    viewHolder.precio.setText("$"+lp.get(i).getPrecio().toString());
                    viewHolder.id.setText(lp.get(i).getId().toString());
                    viewHolder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(c, "Id "+viewHolder.id.getText(), Toast.LENGTH_SHORT).show();
                            CharSequence text = viewHolder.id.getText();

                            Bundle b = new Bundle();
                            int id = Integer.valueOf(text.toString());
                            b.putInt("id", id);
                            Intent intent = new Intent(c, MembresiaSeleccionada.class);
                            intent.putExtras(b);
                            c.startActivity(intent);
                        }
                    });

    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView tipo, descripcion, precio, id;
        CardView card;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            tipo = itemView.findViewById(R.id.tipo);
            descripcion = itemView.findViewById(R.id.desc);
            precio = itemView.findViewById(R.id.precio);
            card = itemView.findViewById(R.id.card);
            id = itemView.findViewById(R.id.id);
        }
    }
}
