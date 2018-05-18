package com.grenoble.miage.metromobilite.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubata on 18/05/18.
 */

public class NearLine {
    // VARIABLES

    private String id;
    private String name;
    private float lon;
    private float lat;
    private List<String> lines = new ArrayList<>();

    // GETTERS AND SETTERS

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getLon() {
        return lon;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public List<String> getLines() {
        return lines;
    }
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    // CONSTRUCTOR

    public NearLine(String id, String name, float lon, float lat, List<String> lines) {
        this.id = id;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        for (String line : lines) {
            this.lines.add(line);
        }
    }

    public NearLine () {}

    // METHODS

    @Override
    public String toString() {
        return "NearLine " +
                id +
                ", " + name +
                ", " + lon +
                ", " + lat +
                ", " + lines.toString();
    }

}
