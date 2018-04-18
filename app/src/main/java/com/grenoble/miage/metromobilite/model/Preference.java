package com.grenoble.miage.metromobilite.model;

public class Preference {

    private String stopCode;
    private String stopName;

    private String lineShortName;
    private String lineLongName;

    private String direction;
    private boolean isMute;

    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getLineShortName() {
        return lineShortName;
    }

    public void setLineShortName(String lineShortName) {
        this.lineShortName = lineShortName;
    }

    public String getLineLongName() {
        return lineLongName;
    }

    public void setLineLongName(String lineLongName) {
        this.lineLongName = lineLongName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public Preference(String stopCode, String stopName, String lineShortName, String lineLongName, String direction, boolean isMute) {
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.lineShortName = lineShortName;
        this.lineLongName = lineLongName;
        this.direction = direction;
        this.isMute = isMute;
    }

    public Preference(){}
}
