package com.ghrc.picpay.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Guilherme on 27/08/2017.
 */
public class CheckConnection {

    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm;
        NetworkInfo info = null;
        try {
            cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();

        } catch (Exception e) {
            Log.e("connectivity", e.toString());
        }
        return info != null && info.isConnected();
    }
}
