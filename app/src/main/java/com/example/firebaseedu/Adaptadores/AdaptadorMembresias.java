package com.example.firebaseedu.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class AdaptadorMembresias extends RecyclerView.Adapter<AdaptadorMembresias.ViewHolder> {
    @NonNull
    @Override
    public AdaptadorMembresias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMembresias.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
