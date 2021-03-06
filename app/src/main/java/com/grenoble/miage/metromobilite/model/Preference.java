package com.grenoble.miage.metromobilite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Doit être parcelable car passé en intent
 */
public class Preference implements Parcelable {

    private String stopCode;
    private String stopName;

    private String lineId;
    private String lineName;
    private String color;

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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Preference(String stopCode, String stopName, String lineId, String lineName, String color, String direction, boolean isMute) {
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.lineId = lineId;
        this.lineName = lineName;
        this.direction = direction;
        this.isMute = isMute;
        this.color = color;
    }

    public Preference(){}

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stopCode);
        dest.writeString(stopName);
        dest.writeString(getLineId());
        dest.writeString(getLineName());
        dest.writeString(color);
        dest.writeString(direction);
        dest.writeString(String.valueOf(isMute));
    }

    public static final Parcelable.Creator<Preference> CREATOR = new Parcelable.Creator<Preference>()
    {
        @Override
        public Preference createFromParcel(Parcel source)
        {
            return new Preference(source);
        }

        @Override
        public Preference[] newArray(int size)
        {
            return new Preference[size];
        }
    };

    public Preference(Parcel in) {
        this.stopCode = in.readString();
        this.stopName = in.readString();
        this.lineId = in.readString();
        this.lineName = in.readString();
        this.color = in.readString();
        this.direction = in.readString();
        this.isMute = Boolean.getBoolean(in.readString());
    }
}
