package com.ghrc.picpay.util;

import android.app.Application;

import com.facebook.stetho.Stetho;

import static okhttp3.internal.Internal.instance;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class MyApplication extends Application {
    private static BD bd;
    private static MyApplication singleton;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
    public MyApplication getInstance(){
        return singleton;
    }
    public BD getInstanceBD(){
        if (bd == null){
            bd = new BD(getApplicationContext());
        }
        return bd;
    }



}
