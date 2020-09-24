package com.sakura.ydhyfinal.utils;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}