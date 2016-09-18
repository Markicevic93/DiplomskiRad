package com.nemanjaasuv1912.systemForCollaborativeLearning;

import android.app.Application;
import android.content.Context;

/**
 * Created by nemanjamarkicevic on 8/7/16.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
