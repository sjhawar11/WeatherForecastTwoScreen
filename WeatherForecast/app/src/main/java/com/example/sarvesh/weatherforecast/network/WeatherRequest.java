package com.example.sarvesh.weatherforecast.network;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.sarvesh.weatherforecast.model.WeatherApiModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public class WeatherRequest<T> extends Request<T> {
    private final String TAG = WeatherRequest.class.getSimpleName();
    private Type mResponseType;
    private Response.Listener<T> mListener;
    protected static final String PROTOCOL_CHARSET = "utf-8";

    public WeatherRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Type responseType) {
        super(method, url, errorListener);
        mListener = listener;
        mResponseType = responseType;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            T parsedResponse = getParsedResponse(jsonString);
            return Response.success(parsedResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return Response.error(new ParseError(ex));
        }
    }

    private T getParsedResponse(String parsed) {
        Gson gson = new Gson();
        T parsedResponse = gson.fromJson(parsed, mResponseType);
        return parsedResponse;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
