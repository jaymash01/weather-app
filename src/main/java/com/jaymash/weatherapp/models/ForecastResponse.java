package com.jaymash.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("cod")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("cnt")
    private int count;

    @SerializedName("list")
    private List<ForecastListItem> list;

    @SerializedName("city")
    private City city;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ForecastListItem> getList() {
        return list;
    }

    public void setList(List<ForecastListItem> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
