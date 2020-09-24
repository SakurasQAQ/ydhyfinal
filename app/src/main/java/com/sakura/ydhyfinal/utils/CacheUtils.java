package com.sakura.ydhyfinal.utils;

import android.content.Context;

public class CacheUtils {
    //写入缓存
    //key为本段json数据的为一标识
    public static void setCache(Context ctx, String key, String json){
        //以url为文件名，json为内容保存在sd中
        PrefUtils.putString(ctx, key, json);

    }

    public static String getCache(Context ctx,String key){
        return PrefUtils.getString(ctx,key,null);
    }
}
