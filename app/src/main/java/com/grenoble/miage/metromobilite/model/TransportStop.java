package com.grenoble.miage.metromobilite.model;

/**
 * Created by rubata on 21/03/18.
 */

public class TransportStop {

    // VARIABLES

    private String code;
    private String city;
    private String name;
    private double lon;
    private double lat;

    // GETTERS AND SETTERS

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    // CONSTRUCTOR

    public TransportStop(String code, String city, String name, double lon, double lat) {
        this.code = code;
        this.city = city;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }
    public TransportStop() {}

    // METHODS
    @Override
    public String toString() {
        return "TransportStop " +
                code +
                ", " + city +
                ", " + name +
                ", " + lon +
                ", " + lat;
    }

}
