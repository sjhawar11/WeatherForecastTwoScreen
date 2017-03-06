package com.example.sarvesh.weatherforecast.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarvesh.weatherforecast.Adapters.CityDataAdapter;
import com.example.sarvesh.weatherforecast.R;
import com.example.sarvesh.weatherforecast.WeatherDataAdapter;
import com.example.sarvesh.weatherforecast.CommonUtils.WeatherPreferences;

import java.util.ArrayList;

/**
 * Created by Amul on 3/5/2017.
 */

public class CityDataFragment extends Fragment {
    private CityDataAdapter cityDataAdapter;
    public ArrayList<String> cities;
    public CityDataFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multiple_cities_layout, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.cityRecyclerView);

        fetchCities();

        cityDataAdapter = new CityDataAdapter(getActivity(), R.layout.city_row_layout);
        if(cities!=null && cities.size()>0)
            cityDataAdapter.update(cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(cityDataAdapter);
        return view;
    }

    public void fetchCities(){
        cities = WeatherPreferences.getInstance(getActivity()).getCityFromLocal();
        update(cities);
    }

    public void update(ArrayList<String> cities) {
        if(cityDataAdapter != null){
            cityDataAdapter.update(cities);
            cityDataAdapter.notifyDataSetChanged();
        }
    }
}
