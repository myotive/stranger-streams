package com.example.myotive.strangerstreamsdemo;

import com.example.myotive.codemash_common.BaseApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by myotive on 10/16/2016.
 */

public class StrangerStreamsApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            Realm.init(this);
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
            Realm.setDefaultConfiguration(realmConfiguration);
        }
    }
}
