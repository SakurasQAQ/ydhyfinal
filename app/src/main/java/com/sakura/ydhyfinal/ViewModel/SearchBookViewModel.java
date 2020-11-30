package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.gsonres.Get_Ranks;
import com.sakura.ydhyfinal.gsonres.Get_searchResult;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBookViewModel extends AndroidViewModel {


    public SearchBookViewModel(@NonNull Application application) {
        super(application);
    }

    private Get_searchResult getSearchResult;

    public Get_searchResult getGetSearchResult() {
        return getSearchResult;
    }

    private ArrayList<Booksinfo> list = new ArrayList<>();

    public int getNnnumber() {
        return nnnumber;
    }

    private int nnnumber;

    private MutableLiveData<Integer> exsitnone;

    public MutableLiveData<Integer> getExsitnone() {
        if(exsitnone == null){
            exsitnone = new MutableLiveData<>();
            exsitnone.setValue(0);
        }
        return exsitnone;
    }

    private MutableLiveData<List<Booksinfo>> LLlist;

    public MutableLiveData<List<Booksinfo>> getLLlist() {
        if(LLlist == null){
            LLlist = new MutableLiveData<>();
        }
        return LLlist;
    }

    public void GetSearchBookslist(String keywords){

        list.clear();

        HashMap map = new HashMap();

        map.put("bookName",keywords);

        RequestManager requestManager = new RequestManager(getApplication());
        requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                getSearchResult = gson.fromJson(String.valueOf(result),Get_searchResult.class);
                nnnumber = getSearchResult.getTotalPage();


                Booksinfo booksinfo;
                for (int i = 0;i<getSearchResult.getDataList().size();i++){
                    booksinfo = new Booksinfo();
                    booksinfo.setBooksId(getSearchResult.getDataList().get(i).getId());
                    booksinfo.setBooksName(getSearchResult.getDataList().get(i).getTitle());
                    booksinfo.setBooksimgurl(getSearchResult.getDataList().get(i).getCoverImg());

                    list.add(booksinfo);


                }
                if(getSearchResult.getTotalPage() != 0){
                    LLlist.setValue(list);
                }else{
                    exsitnone.setValue(1);
                }



            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

}
