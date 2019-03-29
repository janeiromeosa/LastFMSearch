package com.example.lastfmMVVMJava;

import android.app.Application;

import com.example.lastfmMVVMJava.di.AppComponent;
import com.example.lastfmMVVMJava.di.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
