package com.example.dam2_m08_uf2_t1.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    private Context context;
    private List<Estacion> listaEstaciones;
    private RecyclerViewInterface rvi;

    public Adaptador(Context context, List<Estacion> listaEstaciones, RecyclerViewInterface rvi) {
        this.context = context;
        this.listaEstaciones = listaEstaciones;
        this.rvi = rvi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview, parent, false);
        return new MyViewHolder(view, rvi);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Estacion estacion = listaEstaciones.get(position);

        // Asigna los valores a los TextView
        holder.textViewDireccion.setText(estacion.getAddress());
        holder.textViewEstado.setText(estacion.getStatus());
        holder.textViewBicisDisponibles.setText("Bicis disponibles: " + estacion.getCapacity());
    }

    @Override
    public int getItemCount() {
        return listaEstaciones.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDireccion;
        TextView textViewEstado;
        TextView textViewBicisDisponibles;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);

            textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
            textViewBicisDisponibles = itemView.findViewById(R.id.textViewBicisDisponibles);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rvi != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            rvi.onItemCLick(pos);
                        }
                    }
                }
            });
        }
    }
}
