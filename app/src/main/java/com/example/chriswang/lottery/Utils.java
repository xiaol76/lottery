package com.example.chriswang.lottery;

import android.util.TypedValue;

/**
 * Created by chriswang on 18/1/24.
 */

public class Utils {

    public static int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, LotteryApplication.getInstance().getResources().getDisplayMetrics());
    }
}
