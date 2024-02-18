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
    private boolean isChargingStation;//hay 2 de estos cual quitamos?
    private double nearbyDistance;
    private boolean rideCodeSupport;
    private Object rentalUris; // Puedes ajustar el tipo según la estructura real

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


    // Getters y setters (puedes generarlos automáticamente en la mayoría de las IDEs)

    public int getNum_bikes_available() {
        return num_bikes_available;
    }

    public void setNum_bikes_available(int num_bikes_available) {
        this.num_bikes_available = num_bikes_available;
    }

    public int getBikes_mechanical() {
        return bikes_mechanical;
    }

    public void setBikes_mechanical(int bikes_mechanical) {
        this.bikes_mechanical = bikes_mechanical;
    }

    public int getBikes_ebike() {
        return bikes_ebike;
    }

    public void setBikes_ebike(int bikes_ebike) {
        this.bikes_ebike = bikes_ebike;
    }

    public int getNum_docks_available() {
        return num_docks_available;
    }

    public void setNum_docks_available(int num_docks_available) {
        this.num_docks_available = num_docks_available;
    }

    public int getLast_reported() {
        return last_reported;
    }

    public void setLast_reported(int last_reported) {
        this.last_reported = last_reported;
    }

    public boolean isIs_charging_station() {
        return is_charging_station;
    }

    public void setIs_charging_station(boolean is_charging_station) {
        this.is_charging_station = is_charging_station;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_installed() {
        return is_installed;
    }

    public void setIs_installed(int is_installed) {
        this.is_installed = is_installed;
    }

    public int getIs_renting() {
        return is_renting;
    }

    public void setIs_renting(int is_renting) {
        this.is_renting = is_renting;
    }

    public int getIs_returning() {
        return is_returning;
    }

    public void setIs_returning(int is_returning) {
        this.is_returning = is_returning;
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
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

    public boolean isChargingStation() {
        return isChargingStation;
    }

    public void setChargingStation(boolean chargingStation) {
        isChargingStation = chargingStation;
    }

    public double getNearbyDistance() {
        return nearbyDistance;
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

    public Object getRentalUris() {
        return rentalUris;
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

    public String getRefEstacionCercana() {
        return refEstacionCercana;
    }

    public void setRefEstacionCercana(String refEstacionCercana) {
        this.refEstacionCercana = refEstacionCercana;
    }

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
        // Aquí puedes seguir escribiendo los demás campos en el Parcel
    }

    // Constructor utilizado para crear un objeto Estacion a partir de un Parcel
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
        // Aquí puedes seguir leyendo los demás campos del Parcel
    }

    // Implementación del Creator utilizado para crear instancias de Estacion a partir del Parcel
    public static final Creator<Estacion> CREATOR = new Creator<Estacion>() {
        @Override
        public Estacion createFromParcel(Parcel in) {
            return new Estacion(in);
        }

        @Override
        public Estacion[] newArray(int size) {
            return new Estacion[size];
        }
    };
// Otros métodos si es necesario
}
