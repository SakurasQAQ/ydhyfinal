package com.sakura.ydhyfinal.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;

public class ChangeTime {

    public static String format(String date) {
        if (TextUtils.isEmpty(date))
            return null;
        //需要其他格式的以此类推
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        Long time = new Long(date);
        return format.format(time);
    }

    public static String formatdate(String date) {
        if (TextUtils.isEmpty(date))
            return null;
        //需要其他格式的以此类推
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Long time = new Long(date);
        return format.format(time);
    }
}
