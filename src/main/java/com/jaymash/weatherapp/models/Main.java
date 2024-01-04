package com.jaymash.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private double temperature;

    @SerializedName("feels_like")
    private double feelsLike;

    @SerializedName("temp_min")
    private double minTemperature;

    @SerializedName("temp_max")
    private double maxTemperature;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("sea_level")
    private double seaLevel;

    @SerializedName("grnd_level")
    private double groundLevel;

    @SerializedName("humidity")
    private double humidity;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
