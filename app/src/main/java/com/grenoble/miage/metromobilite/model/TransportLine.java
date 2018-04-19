package com.grenoble.miage.metromobilite.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Doit être parcelable afin d'être transmit dans un intent
 * Created by rubata on 21/03/18.
 */
public class TransportLine implements Parcelable {

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
    public TransportLine () {}

    // METHODS

    @Override
    public String toString() {
        return "TransportLine " +
                id +
                ", " + shortName +
                ", " + longName +
                ", " + color +
                ", " + textColor +
                ", " + mode +
                ", " + type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortName);
        dest.writeString(longName);
        dest.writeString(color);
        dest.writeString(textColor);
        dest.writeString(mode);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<TransportLine> CREATOR = new Parcelable.Creator<TransportLine>()
    {
        @Override
        public TransportLine createFromParcel(Parcel source)
        {
            return new TransportLine(source);
        }

        @Override
        public TransportLine[] newArray(int size)
        {
            return new TransportLine[size];
        }
    };

    public TransportLine(Parcel in) {
        this.id = in.readString();
        this.shortName = in.readString();
        this.longName = in.readString();
        this.color = in.readString();
        this.textColor = in.readString();
        this.mode = in.readString();
        this.type = in.readString();
    }


}