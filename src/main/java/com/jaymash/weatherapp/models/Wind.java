package com.jaymash.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private double degrees;

    @SerializedName("gust")
    private double gust;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
