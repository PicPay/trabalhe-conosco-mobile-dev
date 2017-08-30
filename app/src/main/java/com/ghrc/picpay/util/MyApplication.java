package com.ghrc.picpay.util;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.ghrc.picpay.api.PicPayApi;
import com.ghrc.picpay.deserialize.UserDes;
import com.ghrc.picpay.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class MyApplication extends Application {
    private static BD bd;
    private static Retrofit retrofit;
    private static PicPayApi apiInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
    private Retrofit getRetrofitInstance(){
        if( retrofit == null){
            Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserDes()).create();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Config.API)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public PicPayApi getApiInstance(){
        if (apiInstance == null){
            apiInstance = getRetrofitInstance().create(PicPayApi.class);
        }
        return apiInstance;
    }

    public BD getInstanceBD(){
        if (bd == null){
            bd = new BD(getApplicationContext());
        }
        return bd;
    }



}
