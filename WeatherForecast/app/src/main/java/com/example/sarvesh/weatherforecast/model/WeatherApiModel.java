package com.example.sarvesh.weatherforecast.model;

import java.util.ArrayList;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public class WeatherApiModel {
    private City city;
    private ArrayList<DailyWeather> list;
    private String cod;
    private int cnt;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<DailyWeather> getList() {
        if(list == null)
            list = new ArrayList<DailyWeather>();
        return list;
    }

    public void setList(ArrayList<DailyWeather> dailyWeatherArrayList) {
        this.list = dailyWeatherArrayList;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
