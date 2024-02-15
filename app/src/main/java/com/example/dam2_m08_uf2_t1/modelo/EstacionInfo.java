package com.example.dam2_m08_uf2_t1.modelo;

public class EstacionInfo {
    private String address;
    private String status;
    private int bikesAvailable;

    public EstacionInfo(String address, String status, int bikesAvailable) {
        this.address = address;
        this.status = status;
        this.bikesAvailable = bikesAvailable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBikesAvailable() {
        return bikesAvailable;
    }

    public void setBikesAvailable(int bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }
}
