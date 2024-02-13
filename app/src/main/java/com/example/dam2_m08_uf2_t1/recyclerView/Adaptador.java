package com.example.dam2_m08_uf2_t1.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    Context context;
    RecyclerViewInterface rvi;

    public Adaptador(Context context, RecyclerViewInterface rvi) {
        this.context = context;

        this.rvi = rvi;
    }


    @NonNull
    @Override
    public Adaptador.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (true){

            notifyDataSetChanged();

        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview,parent,false);
        return new Adaptador.MyViewHolder(view, rvi);

    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitulo;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.T1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (rvi != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            rvi.onItemCLick(pos);
                        }
                    }
                }
            });
        }

    }
}

