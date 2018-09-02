package com.example.camera;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Android-mwb on 2018/7/26.
 */
public class MyApplication extends Application {
    public static Context applicationContext;
    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        initScreenSize();
    }

    private void initScreenSize() {
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        mScreenWidth = mDisplayMetrics.widthPixels;
        mScreenHeight = mDisplayMetrics.heightPixels;

    }
}
