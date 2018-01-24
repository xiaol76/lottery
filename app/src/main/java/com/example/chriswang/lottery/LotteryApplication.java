package com.example.chriswang.lottery;

import android.app.Application;
import android.content.Context;

/**
 * Created by chriswang on 18/1/24.
 */

public class LotteryApplication extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = getApplicationContext();
    }

    public static Context getInstance() {
        return sInstance;
    }
}
