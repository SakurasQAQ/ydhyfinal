package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.bean.Animals;
import com.sakura.ydhyfinal.gsonres.Get_Animals;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalChooseViewModel extends AndroidViewModel {

    final public String CURRENTURL =  "/mobileCreature/getCreatures";

    private Get_Animals animals;



    private ArrayList Imagelist = new ArrayList();

    public ArrayList getImagelist() {
        return Imagelist;
    }

    private ArrayList<Animals> animalsinfoList = new ArrayList<>();

    public ArrayList<Animals> getAnimalsinfoList() {
        return animalsinfoList;
    }

    //总页数
//    private MutableLiveData<Integer> Totalpage;
//
//    public MutableLiveData<Integer> getTotalpage() {
//        if(Totalpage == null){
//            Totalpage = new MutableLiveData<>();
//            Totalpage.setValue(0);
//        }
//        return Totalpage;
//    }
    int Totalpage = 0;

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

    public void getOnlineAnimals(String userid,String category,boolean jude){

        HashMap map = new HashMap();

        map.put("userId",userid);
        if(jude){
            map.put("category",category);
        }


        RequestManager requestManager = new RequestManager(getApplication());
        requestManager.requestAsyn(CURRENTURL, 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                animals = gson.fromJson(String.valueOf(result),Get_Animals.class);



                //设置图片存放数组
                for(int i = 0;i<animals.getDataList().size();i++){
                    Animals animalsinfo = new Animals();
                    animalsinfo.setImg(animals.getDataList().get(i).getImg());
                    animalsinfo.setHasNum(animals.getDataList().get(i).getHasNum());
                    animalsinfo.setId(animals.getDataList().get(i).getId());
                    animalsinfo.setName(animals.getDataList().get(i).getName());
                    animalsinfo.setIntroduction(animals.getDataList().get(i).getIntroduction());
                    animalsinfo.setLevel(animals.getDataList().get(i).getLevel());

                    animalsinfoList.add(animalsinfo);
                }
                Log.d("size", "onReqSuccess: "+animalsinfoList.size()+"  "+animals.getCurrentPage());

                Totalpage = animals.getTotalPage();

                if(Totalpage > 1){
                    getMoreOnlineAnimals(userid,category,Totalpage,jude);
                }else{
                    isfinshload.setValue(1);
                }



            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }



    public void getMoreOnlineAnimals(String userid,String category,int totalpage,boolean jude){

        HashMap map = new HashMap();
        int cuttentpage  = 0;
        map.put("userId",userid);

        if(jude){
            map.put("category",category);
        }

        for(int i = 2;i<=totalpage;i++){
            cuttentpage = i;
            int cttpage = i;
            Log.d("show+++", "getMoreOnlineAnimals: "+i);
            map.put("pageNum",String.valueOf(cuttentpage));

            RequestManager requestManager = new RequestManager(getApplication());
            requestManager.requestAsyn(CURRENTURL, 0, map, new RequestManager.ReqCallBack<Object>() {
                @Override
                public void onReqSuccess(Object result) {

                    Gson gson = new Gson();
                    animals = gson.fromJson(String.valueOf(result),Get_Animals.class);
                    //设置图片存放数组
                    for(int j = 0;j<animals.getDataList().size();j++){
                        Animals animalsinfo = new Animals();
                        animalsinfo.setImg(animals.getDataList().get(j).getImg());
                        animalsinfo.setHasNum(animals.getDataList().get(j).getHasNum());
                        animalsinfo.setId(animals.getDataList().get(j).getId());
                        animalsinfo.setName(animals.getDataList().get(j).getName());
                        animalsinfo.setIntroduction(animals.getDataList().get(j).getIntroduction());
                        animalsinfo.setLevel(animals.getDataList().get(j).getLevel());

                        animalsinfoList.add(animalsinfo);
                    }
                    Log.d("size", "onReqSuccess: "+animalsinfoList.size()+"  "+animals.getCurrentPage());
                    if (cttpage == totalpage){
                        isfinshload.setValue(1);
                    }

                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            });


        }


    }

    public void OrderBook(String userid,String bookid,String animalid){

        HashMap maps = new HashMap();

        maps.put("userId",userid);
        maps.put("bookId",bookid);
        maps.put("creatureId",animalid);

        Log.d("当前order信息：", "userId"+userid+"::::::::::::生物Id"+animalid+"：：：：：：：：：bookId"+bookid);


        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileBook/subscribe", 1, maps, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                Log.d("ding", "订阅成功");

            }

            @Override
            public void onReqFailed(String errorMsg) {
                Log.d("error", "已经订阅");
            }
        });


    }




}
