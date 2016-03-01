package com.johnriggsdev.tictactoex.baseLevelPArts;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import java.lang.Override;

import io.fabric.sdk.android.Fabric;

/**
 * Created by johnriggs on 2/28/16.
 */
public class TttApp extends Application {
    private static TttApp tttApp;

    @Override
    public void onCreate() {
        super.onCreate();
        tttApp = this;

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
    }

    public static TttApp getInstance(){
        return tttApp;
    }
}
