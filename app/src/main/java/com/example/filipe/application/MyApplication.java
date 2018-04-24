package com.example.filipe.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.filipe.util.LruBitmapCache;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by filipe on 23/04/18.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realm-picpay.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static synchronized MyApplication getInstance() {
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }



    //VOLLEY
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        getRequestQueue();
        if(mImageLoader == null){
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
        }
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
