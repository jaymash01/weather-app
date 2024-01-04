package com.jaymash.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Preferences {

    @SerializedName("units")
    private String units;

    @SerializedName("city")
    private City city;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
