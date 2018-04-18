package com.grenoble.miage.metromobilite.model;

import java.util.ArrayList;
import java.util.List;

public class LineArrival {

    String direction;
    List<Arrival> listArrivals;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Arrival> getListArrivals() {
        return listArrivals;
    }

    public LineArrival(){
        direction ="";
        listArrivals = new ArrayList<>();
    }

    public void setListArrivals(List<Arrival> listArrivals) {
        this.listArrivals = listArrivals;
    }

    public void addArrival(Arrival arrival){
        this.listArrivals.add(arrival);
    }
}
