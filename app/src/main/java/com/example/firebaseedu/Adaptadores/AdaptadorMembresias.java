package com.example.firebaseedu.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebaseedu.Modelos.Membresia;
import com.example.firebaseedu.R;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull AdaptadorMembresias.ViewHolder viewHolder, int i) {

                    Picasso.with(c).load(lp.get(i).getImagen()).into(viewHolder.imagen);
                    viewHolder.tipo.setText(lp.get(i).getTipo());
                    viewHolder.descripcion.setText(lp.get(i).getDescripcion());
                    viewHolder.precio.setText("$"+lp.get(i).getPrecio().toString());
    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView tipo, descripcion, precio;
        CardView card;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            tipo = itemView.findViewById(R.id.tipo);
            descripcion = itemView.findViewById(R.id.desc);
            precio = itemView.findViewById(R.id.precio);
            card = itemView.findViewById(R.id.card);
        }
    }
}
