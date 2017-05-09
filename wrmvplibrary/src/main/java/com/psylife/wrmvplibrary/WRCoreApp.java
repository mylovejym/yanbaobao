package com.psylife.wrmvplibrary;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.psylife.wrmvplibrary.utils.SpUtil;


public abstract class WRCoreApp extends Application {
    private static WRCoreApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SpUtil.init(this);
    }

    public static synchronized WRCoreApp getInstance() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    public abstract String setBaseUrl();
}

