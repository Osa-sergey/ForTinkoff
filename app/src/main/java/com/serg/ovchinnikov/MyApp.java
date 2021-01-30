package com.serg.ovchinnikov;

import android.app.Application;

import com.serg.ovchinnikov.Api.GifApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {

    private GifApi gifApi;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://developerslife.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gifApi = retrofit.create(GifApi.class);
    }

    public GifApi getGifApi() {
        return gifApi;
    }
}
