package com.example.sarvesh.weatherforecast;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sarvesh.weatherforecast.Fragment.CityDataFragment;
import com.example.sarvesh.weatherforecast.CommonUtils.WeatherPreferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout searchContainer;
    LinearLayout cityContainer;
    LinearLayout dataContainer;
    ArrayList<String> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchContainer = (LinearLayout) findViewById(R.id.searchContainer);
        cityContainer = (LinearLayout) findViewById(R.id.cityContainer);
        dataContainer = (LinearLayout) findViewById(R.id.dataContainer);
    }


    public void onClick(View view) {
        if(view.getId() == R.id.searchButton){
            EditText editText = (EditText) findViewById(R.id.searchText);
            String city = editText.getText().toString();
            cityContainer.setVisibility(View.VISIBLE);
            dataContainer.setVisibility(View.GONE);
            WeatherPreferences.getInstance(this).addCityToLocal(city);
            cities = WeatherPreferences.getInstance(this).getCityFromLocal();
            FragmentManager manager = getSupportFragmentManager();
            CityDataFragment cityDataFragment = (CityDataFragment) manager.findFragmentById(R.id.cityFragment);
            if(cities!=null)
                cityDataFragment.update(cities);
        }

    }

    @Override
    public void onBackPressed() {
        if(searchContainer.getVisibility()==View.VISIBLE){
            super.onBackPressed();
        }else{
            searchContainer.setVisibility(View.VISIBLE);
            dataContainer.setVisibility(View.GONE);
            cityContainer.setVisibility(View.VISIBLE);
        }

    }
}
