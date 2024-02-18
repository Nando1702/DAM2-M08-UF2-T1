package com.example.dam2_m08_uf2_t1.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.main.SharedPref;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    // Atributos del Adaptador
    private Context context;
    private List<Estacion> listaEstaciones;
    private RecyclerViewInterface rvi;

    // Constructor del Adaptador
    public Adaptador(Context context, List<Estacion> listaEstaciones, RecyclerViewInterface rvi) {
        this.context = context;
        this.listaEstaciones = listaEstaciones;
        this.rvi = rvi;

        System.out.println("adapt--");
        System.out.println(listaEstaciones);
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
        // Obtiene la estación de la posición actual
        Estacion estacion = listaEstaciones.get(position);

        // Asignar los valores a los TextView y el botón en MyViewHolder
        holder.textViewDireccion.setText(estacion.getAddress());
        holder.textViewEstado.setText(estacion.getStatus());
        holder.textViewBicisEDisponibles.setText("Bicis electronicas disponibles: " + estacion.getBikes_ebike());
        holder.textViewBicisMDisponibles.setText("Bicis mecanicas disponibles: " + estacion.getBikes_mechanical());
        holder.botonFav.setText(estacion.isFavorite() != true ? "✩" : "★");

        // Establecer el color del Nombre segun si es Favorito/Abierto/Cerrado
        if (estacion.isFavorite()) {
            holder.textViewDireccion.setTextColor(Color.parseColor("#FFFF00")); // Reemplaza con el drawable para estación abierta
        } else {
            if (estacion.getStatus().equals("IN_SERVICE")) {
                holder.textViewDireccion.setTextColor(Color.parseColor("#FFFF0000")); // Reemplaza con el drawable para estación abierta
            } else {
                holder.textViewDireccion.setTextColor(Color.parseColor("#FF000000")); // Reemplaza con el drawable para estación cerrada
            }
        }
        // Agregar una acción al botón favorito
        holder.botonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Llamar al método onButtonCLick de RecyclerViewInterface con la Posicion
                if (rvi != null) {
                    rvi.onButtonCLick(position);
                }
            }
        });
    }

    // Devuelve el Tamaño de la lista
    @Override
    public int getItemCount() {
        if (listaEstaciones == null){
            return 0;
        }
        return listaEstaciones.size();
    }

    // Setter para modificar la lista del adaptador, cuando la cambia notifica los cambios
    public void setListaEstaciones(List<Estacion> listaEstaciones) {
        this.listaEstaciones = listaEstaciones;
        notifyDataSetChanged();
    }

    // MyViewHolder, cada elemento de la lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDireccion;
        TextView textViewEstado;
        TextView textViewBicisEDisponibles;
        TextView textViewBicisMDisponibles;
        Button botonFav;

        // Constructor del MyViewHolder
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);

            // Enlaza los elementos de la vista con las variables correspondientes
            textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
            textViewBicisMDisponibles = itemView.findViewById(R.id.textViewBicisMDisponibles);
            textViewBicisEDisponibles = itemView.findViewById(R.id.textViewBicisEDisponibles);
            botonFav = itemView.findViewById(R.id.button2);
            // Establecer un listener para cada elemento de la lista
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
