package com.example.dam2_m08_uf2_t1.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Estacion implements Serializable, Parcelable {
    //Estat
    private int stationId;
    private int num_bikes_available;
    private int bikes_mechanical;//bici del tipo mecanica(NORMAL)
    private int bikes_ebike;//bici del tipo electrica
    private int num_docks_available;
    private int last_reported;
    private boolean is_charging_station;
    private String status;
    private int is_installed;
    private int is_renting;
    private int is_returning;
    private int traffic;//trafic null?

    private boolean isFavorite;

    //info esto con seters
    private String name;
    private String physicalConfiguration;
    private double lat;
    private double lon;
    private double altitude;
    private String address;
    private String postCode;
    private int capacity;
    private boolean isChargingStation;
    private double nearbyDistance;
    private boolean rideCodeSupport;
    private Object rentalUris;

    private String refEstacionCercana;
    // Constructor

    public Estacion(int stationId, int num_bikes_available, int bikes_mechanical, int bikes_ebike, int num_docks_available, int last_reported, boolean is_charging_station, String status, int is_installed, int is_renting, int is_returning, int traffic) {
        this.stationId = stationId;
        this.num_bikes_available = num_bikes_available;
        this.bikes_mechanical = bikes_mechanical;
        this.bikes_ebike = bikes_ebike;
        this.num_docks_available = num_docks_available;
        this.last_reported = last_reported;
        this.is_charging_station = is_charging_station;
        this.status = status;
        this.is_installed = is_installed;
        this.is_renting = is_renting;
        this.is_returning = is_returning;
        this.traffic = traffic;
        this.isFavorite = false;
    }


    // Getters y setters
    public int getNum_bikes_available() {
        return num_bikes_available;
    }
    public int getBikes_mechanical() {
        return bikes_mechanical;
    }
    public int getBikes_ebike() {
        return bikes_ebike;
    }
    public int getNum_docks_available() {
        return num_docks_available;
    }
    public int getLast_reported() {
        return last_reported;
    }
    public boolean isIs_charging_station() {
        return is_charging_station;
    }
    public String getStatus() {
        return status;
    }
    public int getStationId() {
        return stationId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhysicalConfiguration() {
        return physicalConfiguration;
    }
    public void setPhysicalConfiguration(String physicalConfiguration) {
        this.physicalConfiguration = physicalConfiguration;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setNearbyDistance(double nearbyDistance) {
        this.nearbyDistance = nearbyDistance;
    }
    public boolean isRideCodeSupport() {
        return rideCodeSupport;
    }
    public void setRideCodeSupport(boolean rideCodeSupport) {
        this.rideCodeSupport = rideCodeSupport;
    }
    public void setRentalUris(Object rentalUris) {
        this.rentalUris = rentalUris;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    // To String para hacer comprovaciones
    @Override
    public String toString() {
        return "Estacion{" +
                "stationId=" + stationId +
                ", num_bikes_available=" + num_bikes_available +
                ", bikes_mechanical=" + bikes_mechanical +
                ", bikes_ebike=" + bikes_ebike +
                ", num_docks_available=" + num_docks_available +
                ", last_reported=" + last_reported +
                ", is_charging_station=" + is_charging_station +
                ", status='" + status + '\'' +
                ", is_installed=" + is_installed +
                ", is_renting=" + is_renting +
                ", is_returning=" + is_returning +
                ", traffic=" + traffic +
                ", name='" + name + '\'' +
                ", physicalConfiguration='" + physicalConfiguration + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", altitude=" + altitude +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", capacity=" + capacity +
                ", isChargingStation=" + isChargingStation +
                ", nearbyDistance=" + nearbyDistance +
                ", rideCodeSupport=" + rideCodeSupport +
                ", rentalUris=" + rentalUris +
                '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }
    // Escribe los valores de la estación en un Parcel para su serialización y paso entre procesos.
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(stationId);
        dest.writeInt(num_bikes_available);
        dest.writeInt(bikes_mechanical);
        dest.writeInt(bikes_ebike);
        dest.writeInt(num_docks_available);
        dest.writeInt(last_reported);
        dest.writeByte((byte) (is_charging_station ? 1 : 0));
        dest.writeString(status);
        dest.writeInt(is_installed);
        dest.writeInt(is_renting);
        dest.writeInt(is_returning);
        dest.writeInt(traffic);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(name);
        dest.writeString(physicalConfiguration);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeDouble(altitude);
        dest.writeString(address);
        dest.writeString(postCode);
        dest.writeInt(capacity);
        dest.writeByte((byte) (isChargingStation ? 1 : 0));
        dest.writeDouble(nearbyDistance);
        dest.writeByte((byte) (rideCodeSupport ? 1 : 0));
    }
    // Constructor para crear una instancia de la clase Parcelable a partir de un Parcel.
    protected Estacion(Parcel in) {
        stationId = in.readInt();
        num_bikes_available = in.readInt();
        bikes_mechanical = in.readInt();
        bikes_ebike = in.readInt();
        num_docks_available = in.readInt();
        last_reported = in.readInt();
        is_charging_station = in.readByte() != 0;
        status = in.readString();
        is_installed = in.readInt();
        is_renting = in.readInt();
        is_returning = in.readInt();
        traffic = in.readInt();
        isFavorite = in.readByte() != 0;
        name = in.readString();
        physicalConfiguration = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        altitude = in.readDouble();
        address = in.readString();
        postCode = in.readString();
        capacity = in.readInt();
        isChargingStation = in.readByte() != 0;
        nearbyDistance = in.readDouble();
        rideCodeSupport = in.readByte() != 0;
    }
    // crea instancias de la clase Parcelable a partir de un Parcel.
    public static final Creator<Estacion> CREATOR = new Creator<Estacion>() {
        // Crea una nueva instancia de la clase Parcelable a partir del Parcel proporcionado.
        @Override
        public Estacion createFromParcel(Parcel in) {
            return new Estacion(in);
        }
        // Crea un nuevo array de la clase Parcelable.
        @Override
        public Estacion[] newArray(int size) {
            return new Estacion[size];
        }
    };
}
