package com.sakura.ydhyfinal.main.work;

import android.app.Application;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.gsonres.Get_MobleBlocks_Booksblocks;
import com.sakura.ydhyfinal.gsonres.Get_MobleBlocks_Booksblocks_data;
import com.sakura.ydhyfinal.utils.RequestManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkItemViewModel extends AndroidViewModel {

    protected final static String URL = "mobileForum/getBlocks?category=";
    //protected final static String URLS = "/mobileForum/blockDetail?category=";

    protected Get_MobleBlocks_Booksblocks_data[] getbacklist = new Get_MobleBlocks_Booksblocks_data[10];
    protected Get_MobleBlocks_Booksblocks_data[] getbacklistmore = new Get_MobleBlocks_Booksblocks_data[10];

    public MutableLiveData<Integer> itemstatus;


    public MutableLiveData<Integer> getItemstatus() {
        if(itemstatus == null){
            itemstatus = new MutableLiveData<>();
            itemstatus.setValue(0);
        }

        return itemstatus;
    }

    public MutableLiveData<Integer> getCurrentpager() {

        if(currentpager == null){
            currentpager = new MutableLiveData<>();
            currentpager.setValue(0);
        }
        return currentpager;
    }

    private MutableLiveData<Integer> currentpager;


    public WorkItemViewModel(@NonNull Application application) {
        super(application);
    }


    protected void getonlineData(String cage,int pos){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Glable.GLABLEURL+URL+cage).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                processdata(res, pos);
                itemstatus.postValue(1);
            }
        });

        Log.d("stag", "getonlineData: "+cage);


    }

    protected void processdata(String json,int nums){

        Gson gson = new Gson();
        getbacklist[nums] = gson.fromJson(json, Get_MobleBlocks_Booksblocks_data.class);

    }

    protected void getonlineDatanumbers(int pagenums,int pos,String cages){

        HashMap map = new HashMap();

        map.put("category",cages);
        map.put("pageNum",String.valueOf(pagenums));

        RequestManager requestManager = new RequestManager(getApplication());
        requestManager.requestAsyn("mobileForum/getBlocks", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                getbacklistmore[pos] = gson.fromJson(String.valueOf(result),Get_MobleBlocks_Booksblocks_data.class);

                Log.d("showdata", "onReqSuccess: "+getbacklistmore[pos].getCurrentPage());

                currentpager.postValue(pagenums);

                //currentpagenumber.setValue(getbacklistmore[pos].getCurrentPage());

//                if(currentpagenumber == totalpagenumber){
//                    ismore.setValue(1);
//                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }








}
