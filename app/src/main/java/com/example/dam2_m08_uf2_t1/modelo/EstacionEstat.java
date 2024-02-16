package com.example.dam2_m08_uf2_t1.modelo;

public class EstacionEstat {

    private String direccion;
    private String estado;

    private int capacidad;

    public EstacionEstat(String direccion, String estado, int capacidad) {
        this.direccion = direccion;
        this.estado = estado;
        this.capacidad = capacidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
