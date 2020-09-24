package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_MobileBook_GetBooks;
import com.sakura.ydhyfinal.gsonres.Get_MobleBooks_Getdetails;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksFragDetailViewModel extends AndroidViewModel {

    private HashMap map = new HashMap<>();
    private HashMap bookdetailsmap = new HashMap<>();
    private int lognum = 0;


    public Get_MobleBooks_Getdetails[] getdetails = new Get_MobleBooks_Getdetails[10];

    public Get_MobileBook_GetBooks getbacklist;


    private MutableLiveData<List<Get_MobleBooks_Getdetails[]>> mListData;
    public MutableLiveData<List<Get_MobleBooks_Getdetails[]>> getmListData(){

        if(mListData == null){
                mListData = new MutableLiveData<>();
        }
        return mListData;
    }


    private MutableLiveData<Integer> judes;
    public MutableLiveData<Integer> getJudes() {
        if(judes == null){
            judes = new MediatorLiveData<>();
            judes.setValue(0);
        }

        return judes;
    }

    public BooksFragDetailViewModel(@NonNull Application application) {
        super(application);
    }



    public void getOnlineData(String cage){
        map.put("category",cage);

        Log.d("shows", cage);
        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                getbacklist = gson.fromJson(String.valueOf(result), Get_MobileBook_GetBooks.class);
                //Log.d("details", "onReqSuccess: "+getbacklist.dataList.get(0).getTitle());

                for (int i = 0;i<getbacklist.dataList.size();i++){
                    getbooksdetails(getbacklist.dataList.get(i).getId(),i);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
        
    }

    private void getbooksdetails(String booksid,int nums){

        //Log.d("booksid", "getbooksdetails: "+booksid);

        bookdetailsmap.put("bookId",booksid);
        RequestManager requestManagers = new RequestManager(getApplication());
        requestManagers.requestAsyn("mobileBook/infoBook", 0, bookdetailsmap, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
              //Log.d("shows", "onReqSuccess: "+String.valueOf(result));

              Gson gson = new Gson();
              getdetails[nums] = gson.fromJson(String.valueOf(result),Get_MobleBooks_Getdetails.class);

                Log.d("数字", "onReqSuccess: "+nums);

                lognum++;

                if(lognum==9){
                    judes.setValue(1);
                }



            }

            @Override
            public void onReqFailed(String errorMsg) {

            }

        });

    }





}
