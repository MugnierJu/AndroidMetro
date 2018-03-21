package com.grenoble.miage.metromobilite.model;

import android.graphics.Color;

/**
 * Created by rubata on 21/03/18.
 */

public class TransportLine {

    // VARIABLES

    private String id;
    private String shortName;
    private String longName;
    private String color;
    private String textColor;
    private String mode;
    private String type;

    // GETTERS AND SETTERS

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getLongName() {
        return longName;
    }
    public void setLongName(String longName) {
        this.longName = longName;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getTextColor() {
        return textColor;
    }
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    // CONSTRUCTOR

    public TransportLine(String id, String shortName, String longName, String color, String textColor, String mode, String type) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.color = color;
        this.textColor = textColor;
        this.mode = mode;
        this.type = type;
    }

}