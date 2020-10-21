package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_Animals;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AnimalChooseViewModel extends AndroidViewModel {

    final public String CURRENTURL =  "/mobileCreature/getCreatures";

    private Get_Animals animals;

    private ArrayList Imagelist = new ArrayList();

    public ArrayList getImagelist() {
        return Imagelist;
    }

    //总页数
    private MutableLiveData<Integer> Totalpage;

    public MutableLiveData<Integer> getTotalpage() {
        if(Totalpage == null){
            Totalpage = new MutableLiveData<>();
            Totalpage.setValue(0);
        }
        return Totalpage;
    }

    //加载进度
    public MutableLiveData<Integer> isfinshload;

    public MutableLiveData<Integer> getIsfinshload() {
        if(isfinshload == null){
            isfinshload = new MutableLiveData<>();
            isfinshload.setValue(0);
        }
        return isfinshload;
    }

    public AnimalChooseViewModel(@NonNull Application application) {
        super(application);
    }

    public void getOnlineAnimals(String userid,String category){

        HashMap map = new HashMap();

        map.put("userId",userid);
        map.put("category",category);

        RequestManager requestManager = new RequestManager(getApplication());
        requestManager.requestAsyn(CURRENTURL, 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                //Log.d("animals", "onReqSuccess: "+ animals.getDataList().get(2).getName());

                //写一次缓存
                CacheUtils.setCache(getApplication(),CURRENTURL+category+"1",String.valueOf(result));

                processData(String.valueOf(result));



            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    public void processData(String json){
        Gson gson = new Gson();
        animals = gson.fromJson(json,Get_Animals.class);


        //设置图片存放数组
        for(int i = 0;i<animals.getDataList().size();i++){
            Imagelist.add(animals.getDataList().get(i).getImg());
        }

        Totalpage.setValue(animals.getTotalPage());

        if(animals.getTotalPage() == 1){
            isfinshload.setValue(1);
        }

        Log.d("total", "processData: "+Totalpage.getValue());

    }


    public void getMoreOnlineAnimals(String userid,String category){

        HashMap map = new HashMap();
        int cuttentpage  = 0;
        map.put("userId",userid);
        map.put("category",category);

        for(int i = 2;i<=Totalpage.getValue();i++){
            cuttentpage = i;
            int cttpage = i;
            Log.d("show+++", "getMoreOnlineAnimals: "+i);
            map.put("pageNum",String.valueOf(cuttentpage));

            RequestManager requestManager = new RequestManager(getApplication());
            requestManager.requestAsyn(CURRENTURL, 0, map, new RequestManager.ReqCallBack<Object>() {
                @Override
                public void onReqSuccess(Object result) {

                    //写一次缓存
                    CacheUtils.setCache(getApplication(),CURRENTURL+category+cttpage,String.valueOf(result));
                    processMoreData(String.valueOf(result),cttpage);

                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            });


        }


    }

    public void processMoreData(String json,int page){
        Gson gson = new Gson();
        animals = gson.fromJson(json,Get_Animals.class);
        //设置图片存放数组
        for(int j = 0;j<animals.getDataList().size();j++){
            Imagelist.add(animals.getDataList().get(j).getImg());
        }
        Log.d("size", "onReqSuccess: "+Imagelist.size()+"  "+animals.getCurrentPage());
        if (page == Totalpage.getValue()){
            isfinshload.setValue(1);
        }
    }



}
