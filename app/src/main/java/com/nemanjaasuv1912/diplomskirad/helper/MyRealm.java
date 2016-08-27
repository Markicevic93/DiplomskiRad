package com.nemanjaasuv1912.diplomskirad.helper;

import com.nemanjaasuv1912.diplomskirad.MyApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nemanjamarkicevic on 8/9/16.
 */
public class MyRealm {

    private static RealmConfiguration realmConfiguration;

    public static Realm getRealm(){
        if(realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration.Builder(MyApplication.getContext()).deleteRealmIfMigrationNeeded().build();
        }

        Realm.setDefaultConfiguration(realmConfiguration);

        return Realm.getDefaultInstance();
    }



}
