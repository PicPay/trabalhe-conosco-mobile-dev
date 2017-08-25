package com.lacourt.picpay.picpaymobiledev;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by igor on 12/08/2017.
 */

public class UserLoader extends AsyncTaskLoader<List<User>> {

//    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private static final String LOG_TAG = "LOG_TAG";
    private String mUrl = "";

    public UserLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        if(mUrl == null || mUrl.isEmpty()){
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        List<User> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;

    }

}
