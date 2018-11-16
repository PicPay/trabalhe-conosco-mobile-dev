package project.picpay.test._util;

import android.util.Log;

import project.picpay.test.BuildConfig;


/**
 * Created by Rodrigo Oliveira on 16/04/2018 for LeadsOnline.
 * ContactModel us rodrigooliveira.tecinfo@gmail.com
 */
public class Logger {

    private final String TAG;
    private final int priority;

    public static Logger withTag(String tag) {
        return new Logger(tag);
    }

    private Logger(String TAG) {
        this.TAG = TAG;
        this.priority = Log.DEBUG;
    }

    public Logger log(String message) {
        if (BuildConfig.DEBUG) {
            Log.println(priority, TAG, message);
        }
        return this;
    }

    public void withCause(Exception cause) {
        if (BuildConfig.DEBUG) {
            Log.println(priority, TAG, Log.getStackTraceString(cause));
        }
    }

}