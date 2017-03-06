package com.example.sarvesh.weatherforecast.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.sarvesh.weatherforecast.R;
import com.example.sarvesh.weatherforecast.WeatherDataFragment;
import com.example.sarvesh.weatherforecast.model.DailyWeather;
import com.example.sarvesh.weatherforecast.model.WeatherApiModel;
import com.example.sarvesh.weatherforecast.network.NetworkManager;
import com.example.sarvesh.weatherforecast.network.OnResponse;
import com.example.sarvesh.weatherforecast.network.WeatherRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarvesh on 3/5/2017.
 */

public class CityDataAdapter extends  RecyclerView.Adapter<CityDataAdapter.CityViewHolder> implements OnResponse, View.OnClickListener {
    private final static String REQUEST_BY_NAME = "http://api.openweathermap.org/data/2.5/forecast/daily?q={CITY_NAME}&mode=json&units=metric&cnt=1&APPID=7eb68a19c6786f425839b6ffa00a6b69";
    private ArrayList<String> cities = new ArrayList<>();
    private WeatherApiModel weatherApiModel = new WeatherApiModel();
    private RequestQueue requestQueue;
    private int mLayout;
    private Activity mContext;
    private HashMap<Integer,Boolean> responseFetchedBoolMap;
    private CityViewHolder cityViewHolder;
    private Map<Integer, WeatherApiModel> responseMap = new HashMap<>();

    public CityDataAdapter(Activity context , int layout){
        mContext = context;
        mLayout = layout;
        requestQueue = NetworkManager.getRequestQueue(context);
        responseFetchedBoolMap = new HashMap<>();
    }

    private void fetchWeatherData(int position) {
        if(cities!=null) {
            responseFetchedBoolMap.put(position, true);
            String url = REQUEST_BY_NAME.replace("{CITY_NAME}", cities.get(position));
            WeatherRequest<WeatherApiModel> request = new WeatherRequest<WeatherApiModel>(Request.Method.GET, url, this, this, WeatherApiModel.class);
            requestQueue.add(request);
        }
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        cityViewHolder = holder;
        if(responseFetchedBoolMap.get(position)==null || !responseFetchedBoolMap.get(position))
            fetchWeatherData(position);
        holder.bindData(responseMap.get(position),position, this);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private CityViewHolder getViewholder(){
        return cityViewHolder;
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof WeatherApiModel) {
            String citYname = ((WeatherApiModel)response).getCity().getName();
            for(int i = 0; i<cities.size();i++){
                if(cities.get(i).equalsIgnoreCase(citYname)){
                    responseMap.put(i, (WeatherApiModel) response);
                }
            }

            notifyDataSetChanged();
        }
    }

    public void update(ArrayList<String> cities) {
        this.cities = cities;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cityContainerLayout){
            android.app.FragmentManager manager = mContext.getFragmentManager();
            WeatherDataFragment weatherDataFragment = (WeatherDataFragment) manager.findFragmentById(R.id.dataFragment);
            weatherDataFragment.update((String) v.getTag());
            View x = v.getRootView();
            LinearLayout container = (LinearLayout) x.findViewById(R.id.searchContainer);
            container.setVisibility(View.GONE);
            LinearLayout cityContainer = (LinearLayout) x.findViewById(R.id.cityContainer);
            cityContainer.setVisibility(View.GONE);
            LinearLayout dataContainer = (LinearLayout) x.findViewById(R.id.dataContainer);
            dataContainer.setVisibility(View.VISIBLE);
        }
    }


    public class CityViewHolder extends RecyclerView.ViewHolder{
        TextView cityNameTextView;
        TextView minTempTextView;
        TextView maxTempTextView;
        View cityContainerLayout;
        public CityViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = (TextView) itemView.findViewById(R.id.cityName);
            minTempTextView =(TextView) itemView.findViewById(R.id.cityeMinTemp);
            maxTempTextView = (TextView) itemView.findViewById(R.id.cityMaxTemp);
            cityContainerLayout = itemView.findViewById(R.id.cityContainerLayout);
        }
        public void bindData(WeatherApiModel weatherApiModel, int position, View.OnClickListener onClickListener){
            if(weatherApiModel!=null && weatherApiModel.getList()!=null && weatherApiModel.getList().size()!=0) {
                DailyWeather weather = weatherApiModel.getList().get(0);
                cityNameTextView.setText(weatherApiModel.getCity().getName());
                minTempTextView.setText(weather.getTemp().getMin());
                maxTempTextView.setText(weather.getTemp().getMax());
                cityContainerLayout.setTag(weatherApiModel.getCity().getName());
                cityContainerLayout.setOnClickListener(onClickListener);
            }
        }
    }
}
