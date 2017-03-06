package com.example.sarvesh.weatherforecast.viewmodel;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.sarvesh.weatherforecast.model.WeatherApiModel;
import com.example.sarvesh.weatherforecast.network.NetworkManager;
import com.example.sarvesh.weatherforecast.network.OnResponse;
import com.example.sarvesh.weatherforecast.network.WeatherRequest;

import java.util.ArrayList;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public class WeatherViewModel implements OnResponse {
    private final static String REQUEST_BY_NAME = "http://api.openweathermap.org/data/2.5/forecast/daily?q={CITY_NAME}&mode=json&units=metric&cnt=14&APPID=93a78fc740c9853c1013e318d1e30797";
    private RequestQueue requestQueue;

    public WeatherViewModel(Context context){
        requestQueue = NetworkManager.getRequestQueue(context);
    }

    private void makeRequestByCityName(String cityName){
        String url = REQUEST_BY_NAME.replace("{CITY_NAME}", cityName);
        WeatherRequest<WeatherApiModel> request = new WeatherRequest<WeatherApiModel>(Request.Method.GET, url, this, this, WeatherApiModel.class );
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {
        if(response instanceof WeatherApiModel){
            WeatherApiModel weatherApiModel = (WeatherApiModel)response;

        }
    }
}
