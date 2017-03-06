package com.example.sarvesh.weatherforecast.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sarvesh on 04-03-2017.
 */

public final class NetworkManager {
    private static RequestQueue requestQueue;
    //private static ImageLoader imageLoader;
    private NetworkManager(){
    }

    public static RequestQueue getRequestQueue(Context context){
        if(requestQueue == null){
            //Using applicatin context to avoid memory leak.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

//    public static ImageLoader getImageLoader(Context context){
//        if(imageLoader == null){
//            imageLoader = new ImageLoader(getRequestQueue(context.getApplicationContext()), new BitmapLruCache());
//        }
//        return imageLoader;
//    }

}