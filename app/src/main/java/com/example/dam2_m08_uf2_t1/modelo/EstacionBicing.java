package com.example.dam2_m08_uf2_t1.modelo;

public class EstacionBicing {

    private String direccion;
    private String estado;
    private int bicisDisponibles;

    public EstacionBicing(String direccion, String estado, int bicisDisponibles) {
        this.direccion = direccion;
        this.estado = estado;
        this.bicisDisponibles = bicisDisponibles;
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

    public int getBicisDisponibles() {
        return bicisDisponibles;
    }

    public void setBicisDisponibles(int bicisDisponibles) {
        this.bicisDisponibles = bicisDisponibles;
    }
}