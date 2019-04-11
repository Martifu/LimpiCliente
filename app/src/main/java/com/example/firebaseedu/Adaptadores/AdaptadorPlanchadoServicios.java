package com.example.firebaseedu.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseedu.Modelos.Membresia;
import com.example.firebaseedu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorPlanchadoServicios extends RecyclerView.Adapter<AdaptadorPlanchadoServicios.ViewHolder> {
    List<Membresia> lp;
    Context c;

    public AdaptadorPlanchadoServicios(List<Membresia> lp, Context c) {
        this.lp = lp;
        this.c = c;
    }

    @NonNull
    @Override
    public AdaptadorPlanchadoServicios.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.planchado_design,viewGroup,false);
       ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPlanchadoServicios.ViewHolder viewHolder, int i) {
        Picasso.with(c).load("https://i.imgur.com/Bp9Kral.png").into(viewHolder.imagen);
        viewHolder.tipo.setText("Jalo");
    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        Button mas, menos, info;
        TextView tipo, cantidad, precio, id;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            tipo = itemView.findViewById(R.id.tipo);
            precio = itemView.findViewById(R.id.precio);
            mas = itemView.findViewById(R.id.mas);
            menos = itemView.findViewById(R.id.menos);
            info = itemView.findViewById(R.id.infor);
            cantidad = itemView.findViewById(R.id.cantidad);
        }
    }
}
