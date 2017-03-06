package com.example.sarvesh.weatherforecast;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarvesh.weatherforecast.network.OnResponse;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public class WeatherDataFragment extends Fragment {

    private WeatherDataAdapter weatherDataAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        weatherDataAdapter = new WeatherDataAdapter(getActivity(), R.layout.weather_row_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(weatherDataAdapter);
        return view;
    }

    public void update(String city) {
        if(weatherDataAdapter != null){
            weatherDataAdapter.update(city);
        }
    }
}
