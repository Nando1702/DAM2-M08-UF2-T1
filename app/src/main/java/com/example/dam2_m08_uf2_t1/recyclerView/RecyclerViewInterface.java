package com.example.dam2_m08_uf2_t1.recyclerView;
public interface RecyclerViewInterface {

    /**
     * Método llamado cuando se hace clic en un elemento de la lista.
     *
     * @param position La posición del elemento en la lista que se ha clicado.
     */
    void onItemCLick(int position);

    /**
     * Método llamado cuando se hace clic en un botón dentro de un elemento de la lista.
     *
     * @param position La posición del elemento en la lista que contiene el botón que se ha clicado.
     */
    void onButtonCLick(int position);
}