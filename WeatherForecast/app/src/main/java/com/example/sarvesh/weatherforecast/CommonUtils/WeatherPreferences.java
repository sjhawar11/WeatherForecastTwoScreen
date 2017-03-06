package com.example.sarvesh.weatherforecast.CommonUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sarvesh on 05/03/17.
 */

public class WeatherPreferences {
    SharedPreferences shared;
    ArrayList<String> arrPackage;
    private final String citySetKet = "CITY_LIST";
    private static WeatherPreferences  weatherPrefObj;


    public static void setSharedPref(SharedPreferences sharedPref){
        if(weatherPrefObj!=null)
            weatherPrefObj.shared = sharedPref;
    }

    private WeatherPreferences(){
        // add values for your ArrayList any where...
        arrPackage = new ArrayList<>();
    }
    public static WeatherPreferences getInstance(Activity activity)
    {
        if (weatherPrefObj == null)
        {
            weatherPrefObj = new WeatherPreferences();
        }
        weatherPrefObj.shared = activity.getSharedPreferences("App_settings", MODE_PRIVATE);
        return weatherPrefObj;
    }


    public void addCityToLocal(String city){
        this.packagesharedPreferences(city);
    }

    public ArrayList<String> getCityFromLocal(){
        this.retriveSharedValue();
        return arrPackage;
    }

    private void packagesharedPreferences(String city) {
        SharedPreferences.Editor editor = shared.edit();
        Set<String> set = new HashSet<String>();
        if(arrPackage!=null && !arrPackage.contains(city))
            arrPackage.add(city);
        set.addAll(arrPackage);
        editor.putStringSet(citySetKet, set);
        editor.apply();
        Log.d("storesharedPreferences",""+set);
    }

    private void retriveSharedValue() {
        Set<String> set = shared.getStringSet(citySetKet, null);
        if(set!=null) {
            for (String city: set) {
                if(!arrPackage.contains(city))
                    arrPackage.add(city);
            }
        }
        Log.d("retrive Preferences",""+set);
    }
}
