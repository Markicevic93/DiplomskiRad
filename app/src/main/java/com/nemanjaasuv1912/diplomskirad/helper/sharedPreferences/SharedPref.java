package com.nemanjaasuv1912.diplomskirad.helper.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.nemanjaasuv1912.diplomskirad.MyApplication;

/**
 * Created by nemanjamarkicevic on 9/11/16.
 */
public class SharedPref {

    private static final String SHARED_PREF_NAME = "sharedPreffName";

    static private SharedPreferences getSharedPref(){
        return MyApplication.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    static public String getString(String key){
        return getSharedPref().getString(key, "");
    }

    static public void putString(String key, String value){
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putString(key, value);
        editor.apply();
    }
}
