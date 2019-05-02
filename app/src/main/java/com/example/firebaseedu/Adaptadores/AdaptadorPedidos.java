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
import android.widget.TextView;

import com.example.firebaseedu.MembresiaSeleccionada;
import com.example.firebaseedu.Modelos.Membresia;
import com.example.firebaseedu.Modelos.Pedidos;
import com.example.firebaseedu.PedidoSeleccionado;
import com.example.firebaseedu.R;

import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolder> {
    List<Pedidos> lp;
    Context c;

    public AdaptadorPedidos(List<Pedidos> lp, Context c) {
        this.lp = lp;
        this.c = c;
    }
    @NonNull
    @Override
    public AdaptadorPedidos.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.pedidos_desing,viewGroup,false);
      ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorPedidos.ViewHolder viewHolder, int i) {
        viewHolder.total.setText("$"+lp.get(i).getTotal().toString());
        viewHolder.articulos.setText(lp.get(i).getArticulos().toString());
        viewHolder.fecha.setText(lp.get(i).getFecha());
        viewHolder.estatus.setText(lp.get(i).getEstatus());
        viewHolder.folio.setText(lp.get(i).getFolio().toString());
//        viewHolder.cardpedido.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                            Toast.makeText(c, "Id "+viewHolder.id.getText(), Toast.LENGTH_SHORT).show();
//                CharSequence text = viewHolder.folio.getText();
//
//                Bundle b = new Bundle();
//                int id = Integer.valueOf(text.toString());
//                b.putInt("id", id);
//                Intent intent = new Intent(c, PedidoSeleccionado.class);
//                intent.putExtras(b);
//                c.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total, articulos, fecha, estatus,folio;
        CardView cardpedido;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            total = itemView.findViewById(R.id.total);
            articulos = itemView.findViewById(R.id.numero_articulos);
            fecha = itemView.findViewById(R.id.fecha);
            estatus = itemView.findViewById(R.id.estatus);
            folio = itemView.findViewById(R.id.folio);
            cardpedido = itemView.findViewById(R.id.card_pedido);
        }
    }
}
