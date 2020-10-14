package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_Booksinfo;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;

public class ShowBooksInfoViewModel extends AndroidViewModel {

    private Get_Booksinfo getBooksinfo;

    public Get_Booksinfo getGetBooksinfo() {
        return getBooksinfo;
    }

    public ShowBooksInfoViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Integer> booksget;

    public MutableLiveData<Integer> getBooksget() {
        if(booksget == null){
            booksget = new MutableLiveData<>();
            booksget.setValue(0);
        }

        return booksget;
    }

    public void getOnlineBooksinfo(String booksid){

        HashMap maps = new HashMap();

        maps.put("bookId",booksid);
        maps.put("schoolId","1000000");
        maps.put("userType","user_type_student");
        maps.put("userId","F2F9105E-B6F8-C2A2-279A-A9DF84701F57");

        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileBook/bookDetail", 0, maps, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                Gson gson = new Gson();
                getBooksinfo = gson.fromJson(String.valueOf(result),Get_Booksinfo.class);

                booksget.setValue(1);


            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

}
