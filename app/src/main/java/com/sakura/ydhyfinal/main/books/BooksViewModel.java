package com.sakura.ydhyfinal.main.books;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.gsonres.Get_MobileBook_GetBooks;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BooksViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    protected static String URL = "mobileBook/getBooks";

    protected int numint = 0;

    protected Get_MobileBook_GetBooks[] getbacklist = new Get_MobileBook_GetBooks[10];

    protected Get_MobileBook_GetBooks getback;

    protected void getBookslistinfo(Callback callback,String cage){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Glable.GLABLEURL+URL+"?category="+cage).build();



        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);
    }

    protected void processData(String json,int nums){
        Gson gson = new Gson();

        getback = gson.fromJson(json,Get_MobileBook_GetBooks.class);

        getbacklist[nums] = gson.fromJson(json,Get_MobileBook_GetBooks.class);



        Log.d("nums", "processData: "+getbacklist[nums].dataList.get(0).getTitle()+nums+"+"+numint);


    }



    protected void CacheprocessData(String json){
        Gson gson = new Gson();

    }



}
