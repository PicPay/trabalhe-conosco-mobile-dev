package com.ghrc.picpay.util;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
