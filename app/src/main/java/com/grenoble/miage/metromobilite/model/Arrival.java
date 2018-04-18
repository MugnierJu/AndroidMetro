package com.grenoble.miage.metromobilite.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Arrival {

    private String stopId;
    private String stopName;
    private int scheduledArrival;
    private int scheduledDeparture;
    private int realtimeArrival;
    private int realtimeDeparture;
    private int arrivalDelay;
    private int departureDelay;
    private boolean timepoint;
    private boolean realtime;
    private int serviceDay;
    private int tripId;

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(int scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public int getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(int scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public int getRealtimeArrival() {
        return realtimeArrival;
    }

    public void setRealtimeArrival(int realtimeArrival) {
        this.realtimeArrival = realtimeArrival;
    }

    public int getRealtimeDeparture() {
        return realtimeDeparture;
    }

    public void setRealtimeDeparture(int realtimeDeparture) {
        this.realtimeDeparture = realtimeDeparture;
    }

    public int getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(int arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public int getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(int departureDelay) {
        this.departureDelay = departureDelay;
    }

    public boolean isTimepoint() {
        return timepoint;
    }

    public void setTimepoint(boolean timepoint) {
        this.timepoint = timepoint;
    }

    public boolean isRealtime() {
        return realtime;
    }

    public void setRealtime(boolean realtime) {
        this.realtime = realtime;
    }

    public int getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(int serviceDay) {
        this.serviceDay = serviceDay;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Arrival(String stopId, String stopName, int scheduledArrival, int scheduledDeparture, int realtimeArrival, int realtimeDeparture, int arrivalDelay, int departureDelay, boolean timepoint, boolean realtime, int serviceDay, int tripId) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.realtimeArrival = realtimeArrival;
        this.realtimeDeparture = realtimeDeparture;
        this.arrivalDelay = arrivalDelay;
        this.departureDelay = departureDelay;
        this.timepoint = timepoint;
        this.realtime = realtime;
        this.serviceDay = serviceDay;
        this.tripId = tripId;
    }

    public String getTime() {
        int timeArrival = scheduledArrival;

        //converting the arrival time
        if(realtimeArrival > 0){
            timeArrival = realtimeArrival;
        }
        long timeArrivalHour = timeArrival/60/60;
        long timeArrivalMinutes = timeArrival/60%60;





        return "Arrivée à "+timeArrivalHour+"h "+timeArrivalMinutes+"min  -  attente : "+getWaitTime();
    }

    /**
     *
     * @return the time to wait the arrival
     */
    public String getWaitTime(){
        int timeArrival = scheduledArrival;

        //converting the arrival time
        if(realtimeArrival > 0){
            timeArrival = realtimeArrival;
        }


        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault().getTimeZone("Europe/Paris"));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long currentSeconds = (System.currentTimeMillis() - c.getTimeInMillis())/1000;
        long secondsTowait = timeArrival-currentSeconds;
        long hourToWait = secondsTowait/60/60;
        long minutesToWait = secondsTowait/60%60;
        String waitTime = "";
        if(hourToWait > 0){
            return hourToWait+"h "+minutesToWait+"m";
        }
        return minutesToWait+"m";
    }
}
