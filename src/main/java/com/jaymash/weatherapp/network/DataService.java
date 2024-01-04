package com.jaymash.weatherapp.network;

import com.jaymash.weatherapp.models.ForecastResponse;
import com.jaymash.weatherapp.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface DataService {

    @GET("/data/2.5/weather")
    @Headers({"Accept: application/json"})
    Call<WeatherResponse> getWeather(@QueryMap HashMap<String, Object> params);

    @GET("/data/2.5/forecast")
    @Headers({"Accept: application/json"})
    Call<ForecastResponse> getForecast(@QueryMap HashMap<String, Object> params);

}
