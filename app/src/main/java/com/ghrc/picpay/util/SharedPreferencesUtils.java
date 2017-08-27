package com.ghrc.picpay.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Guilherme on 25/08/2017.
 */

public class SharedPreferencesUtils {
    public static void saveValue(String value, String key, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public static void saveValue(Context context, String key, boolean value){
        SharedPreferences sharedPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static  boolean getBooleanValue(String key, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return  sharedPref.getBoolean(key,true);
    }

    public static String getStringValue(String key, Context context){
        SharedPreferences sharedPreferencesUtils = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferencesUtils.getString(key,"");
    }
}
