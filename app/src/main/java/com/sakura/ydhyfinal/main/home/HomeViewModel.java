package com.sakura.ydhyfinal.main.home;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.gsonres.GetbookListP2;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel




    protected GetbookListP2 getback;
    protected static String URL = "mobileUser/bookList";



    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    protected void gethomebooks(Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Glable.GLABLEURL+URL).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);
    }

    public void processData(String json){
        //使用GSon解析，生成对象为GetBooklistClass
        Gson gson = new Gson();
        getback = gson.fromJson(json, GetbookListP2.class);
        //Log.i("输出：",getback.getData().dataList.get(0).name);
    }


}
