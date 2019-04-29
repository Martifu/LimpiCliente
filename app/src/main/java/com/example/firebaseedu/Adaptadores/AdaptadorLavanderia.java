package com.example.firebaseedu.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseedu.Modelos.LavanderiaProductos;
import com.example.firebaseedu.Modelos.Productos;
import com.example.firebaseedu.R;
import com.example.firebaseedu.Servicios;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdaptadorLavanderia extends RecyclerView.Adapter<AdaptadorLavanderia.ViewHolder> {
    List<LavanderiaProductos> lp;
    Context c;

    public AdaptadorLavanderia(List<LavanderiaProductos> lp, Context c) {
        this.lp = lp;
        this.c = c;
    }
    @NonNull
    @Override
    public AdaptadorLavanderia.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.lavanderia_design,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorLavanderia.ViewHolder viewHolder, final int i) {
        Picasso.with(c).load(lp.get(i).getImagen()).into(viewHolder.imagen);
        viewHolder.tipo.setText(lp.get(i).getProducto());
        viewHolder.precio.setText(lp.get(i).getPrecio().toString());
        viewHolder.id.setText(lp.get(i).getId().toString());
        viewHolder.detalles.setText(lp.get(i).getDetalles());

        final int[] cantidad = {0};

        viewHolder.mas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cantidad[0]++;
//                int precio = lp.get(i).getPrecio();
//                int textPrecio = Integer.valueOf((String) viewHolder.precio.getText());
//                viewHolder.precio.setText(String.valueOf( textPrecio+precio));
                viewHolder.cantidad.setText(String.valueOf(cantidad[0]));
//                Toast.makeText(c, "Agregado", Toast.LENGTH_SHORT).show();
                Integer id = lp.get(i).getId();
                Servicios.agregados.put(id);
                JSONObject objectID = new JSONObject();
                try {
                    objectID.put("id",id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Servicios.productos.put(objectID);
//                Toast.makeText(c, "pr "+Servicios.agregados.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolder.cantidad.getText().equals("0"))
                {
                    int eliminado=0;
                    cantidad[0]--;
//                    int precio = lp.get(i).getPrecio();
//                    int textPrecio = Integer.valueOf((String) viewHolder.precio.getText());
//                    viewHolder.precio.setText(String.valueOf( textPrecio-precio));
                    viewHolder.cantidad.setText(String.valueOf(cantidad[0]));
                    eliminado = Integer.valueOf((String)  viewHolder.id.getText());
                    boolean el = false;
                    for (int i = 0; el == false; i++)
                    {

                        try {
                            if (Integer.valueOf((Integer) Servicios.agregados.get(i)) == eliminado )
                            {
                                Servicios.agregados.remove(i);
                                el = true;
                                eliminado =0;
                                i = 0;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
//                    Toast.makeText(c, "Eliminado", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(c, "pr "+Servicios.agregados.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        Button mas, menos, info;
        TextView tipo, cantidad, precio, id, detalles;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_lav);
            tipo = itemView.findViewById(R.id.tipoL);
            precio = itemView.findViewById(R.id.precioL);
            id = itemView.findViewById(R.id.idL);
            detalles = itemView.findViewById(R.id.detllesLav);
            cantidad = itemView.findViewById(R.id.cantidadL);
            mas = itemView.findViewById(R.id.masL);
            menos = itemView.findViewById(R.id.menosL);
        }
    }
}
