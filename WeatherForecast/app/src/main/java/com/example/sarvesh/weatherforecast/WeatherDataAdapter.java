package com.example.sarvesh.weatherforecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.sarvesh.weatherforecast.model.DailyWeather;
import com.example.sarvesh.weatherforecast.model.WeatherApiModel;
import com.example.sarvesh.weatherforecast.network.NetworkManager;
import com.example.sarvesh.weatherforecast.network.OnResponse;
import com.example.sarvesh.weatherforecast.network.WeatherRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherDataHolder> implements OnResponse {

    private final static String REQUEST_BY_NAME = "http://api.openweathermap.org/data/2.5/forecast/daily?q={CITY_NAME}&mode=json&units=metric&cnt=14&APPID=7eb68a19c6786f425839b6ffa00a6b69";

    private Context mContext;
    private int mLayout;
    private WeatherApiModel weatherApiModel = new WeatherApiModel();
    private RequestQueue requestQueue;

    public WeatherDataAdapter(Context context, int layout){
        mContext = context;
        mLayout = layout;
        requestQueue = NetworkManager.getRequestQueue(context);
    }

    @Override
    public WeatherDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_row_layout, parent, false);
        return new WeatherDataHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherDataHolder holder, int position) {
        holder.bindData(weatherApiModel, position);
    }

    @Override
    public int getItemCount() {
        return weatherApiModel.getList().size();
    }

    public void update(String city) {
        makeRequestByCityName(city);
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
            weatherApiModel = (WeatherApiModel)response;
            notifyDataSetChanged();
        }
    }

    public class WeatherDataHolder extends RecyclerView.ViewHolder{
        private TextView minTempTv;
        private TextView maxTempTv;
        private TextView dayTv;
        public WeatherDataHolder(View itemView) {
            super(itemView);
            minTempTv = (TextView) itemView.findViewById(R.id.minTempTv);
            maxTempTv = (TextView) itemView.findViewById(R.id.maxTempTv);
            dayTv = (TextView) itemView.findViewById(R.id.dayTv);
        }

        private void bindData(WeatherApiModel weatherApiModel, int position){
            DailyWeather dailyWeather = weatherApiModel.getList().get(position);
            minTempTv.setText("Min: "+ dailyWeather.getTemp().getMin());
            maxTempTv.setText("Max: "+ dailyWeather.getTemp().getMax());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            Calendar cal = Calendar.getInstance();



            cal.setTime(date);
            cal.add(Calendar.DATE, position); //minus number would decrement the days
            String dateString = sdf.format(cal.getTime());
            dayTv.setText(dateString);
//            String currentDateString = DateFormat.getDateTimeInstance().format(cal.getTime());
//            dayTv.setText(currentDateString);

        }
    }
}
