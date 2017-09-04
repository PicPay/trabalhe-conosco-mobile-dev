package br.com.dalcim.picpay;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PicPayApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
