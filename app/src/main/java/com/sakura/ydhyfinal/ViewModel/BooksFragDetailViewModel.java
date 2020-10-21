package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.DataSourse.PageDataSourceFactory;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.gsonres.Get_MobileBook_GetBooks;
import com.sakura.ydhyfinal.gsonres.Get_MobleBooks_Getdetails;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksFragDetailViewModel extends AndroidViewModel {

    private HashMap map = new HashMap<>();
    private HashMap bookdetailsmap = new HashMap<>();
    private HashMap maps = new HashMap<>();
    private int lognum = 0;


    public Get_MobleBooks_Getdetails[] getdetails = new Get_MobleBooks_Getdetails[10];

    public Get_MobileBook_GetBooks getbacklist;



    private MutableLiveData<Integer> isgetdata;

    public MutableLiveData<Integer> getIsgetdata() {
        if(isgetdata == null){
            isgetdata = new MutableLiveData<>();
            isgetdata.setValue(0);
        }

        return isgetdata;
    }
    //paging方法加载更多

    //每页需要加载的数量
    private static final int NUM_PER_PAGE = 10;
    //第一页
    private static final int PAGE_FIRST = 1;
    //当前页码数
    private int mPage = PAGE_FIRST;

    int totalnum;


    public BooksFragDetailViewModel(@NonNull Application application) {
        super(application);
    }

    private ArrayList<MyWorks> mylist = new ArrayList<>();

    private LiveData<PagedList<MyWorks>> mLiveData;

    public LiveData<PagedList<MyWorks>> getmLiveData() {
        initPageList();

        return mLiveData;
    }

    private MutableLiveData<String> currentcage;

    public MutableLiveData<String> getCurrentcage() {
        if (currentcage == null){
            currentcage = new MutableLiveData<>();
            currentcage.setValue("");
        }
        return currentcage;
    }



    private void initPageList() {

        final PositionalDataSource<MyWorks> positionalDataSource = new PositionalDataSource<MyWorks>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<MyWorks> callback) {
                final int position = computeInitialLoadPosition(params, NUM_PER_PAGE);

                maps.put("pageNum",String.valueOf(PAGE_FIRST));
                maps.put("category",currentcage.getValue());

                RequestManager requestManager = RequestManager.getInstance(getApplication());
                requestManager.requestAsyn("mobileBook/getBooks", 0, maps, new RequestManager.ReqCallBack<Object>() {
                    @Override
                    public void onReqSuccess(Object result) {

                        Gson gson = new Gson();
                        getbacklist = gson.fromJson(String.valueOf(result), Get_MobileBook_GetBooks.class);

                        totalnum = Integer.valueOf(getbacklist.getTotalPage());


                        for (int i = 0;i<getbacklist.dataList.size();i++){
                            //getbooksdetails(getbacklist.dataList.get(i).getId(),i);
                            int pages = i;

                            bookdetailsmap.put("bookId",getbacklist.dataList.get(i).getId());
                            RequestManager requestManagers = new RequestManager(getApplication());
                            requestManagers.requestAsyn("mobileBook/infoBook", 0, bookdetailsmap, new RequestManager.ReqCallBack<Object>() {
                                @Override
                                public void onReqSuccess(Object result) {
                                    //Log.d("shows", "onReqSuccess: "+String.valueOf(result));

                                    Gson gson = new Gson();
                                    getdetails[pages] = gson.fromJson(String.valueOf(result),Get_MobleBooks_Getdetails.class);

                                    Log.d("数字", "onReqSuccess: "+pages);

                                    lognum++;

                                    if(lognum==getbacklist.dataList.size()){
                                        MyWorks wks;
                                        for(int j = 0;j<getbacklist.dataList.size();j++){
                                            wks = new MyWorks();
                                            wks.setWorksName(getbacklist.dataList.get(j).getTitle());
                                            wks.setWorksAuthor(getdetails[j].getData().getAuthor());
                                            wks.setWorksPicUrl(getbacklist.dataList.get(j).getCoverImg());
                                            wks.setWorksIntroduction(getdetails[j].getData().getIntroduction());
                                            mylist.add(wks);
                                        }

                                        callback.onResult(mylist,position);
                                        isgetdata.setValue(1);
                                    }

                                }
                                @Override
                                public void onReqFailed(String errorMsg) {

                                }

                            });

                        }

                    }
                    @Override
                    public void onReqFailed(String errorMsg) {
                    }
                });

            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<MyWorks> callback) {
                lognum=0;
                if(mPage<totalnum) {
                    mPage++;
                    Log.d("show", "loadRange: "+totalnum+"++++"+mPage);
                    map.put("pageNum",String.valueOf(mPage));
                    map.put("category",currentcage.getValue());

                    RequestManager requestManager = new RequestManager(getApplication());
                    requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
                        @Override
                        public void onReqSuccess(Object result) {

                            Gson gson = new Gson();
                            getbacklist = gson.fromJson(String.valueOf(result), Get_MobileBook_GetBooks.class);

                            for (int i = 0;i<getbacklist.dataList.size();i++){
                                //getbooksdetails(getbacklist.dataList.get(i).getId(),i);
                                int pages = i;

                                bookdetailsmap.put("bookId",getbacklist.dataList.get(i).getId());
                                RequestManager requestManagers = new RequestManager(getApplication());
                                requestManagers.requestAsyn("mobileBook/infoBook", 0, bookdetailsmap, new RequestManager.ReqCallBack<Object>() {
                                    @Override
                                    public void onReqSuccess(Object result) {
                                        //Log.d("shows", "onReqSuccess: "+String.valueOf(result));

                                        Gson gson = new Gson();
                                        getdetails[pages] = gson.fromJson(String.valueOf(result),Get_MobleBooks_Getdetails.class);

                                        Log.d("数字", "onReqSuccess: "+pages);

                                        lognum++;


                                        if(lognum==getbacklist.dataList.size()){
                                            mylist = new ArrayList<>();
                                            MyWorks wks;
                                            for(int i = 0;i<getbacklist.dataList.size();i++){
                                                wks = new MyWorks();
                                                wks.setWorksName(getbacklist.dataList.get(i).getTitle());
                                                wks.setWorksAuthor(getdetails[i].getData().getAuthor());
                                                wks.setWorksPicUrl(getbacklist.dataList.get(i).getCoverImg());
                                                wks.setWorksIntroduction(getdetails[i].getData().getIntroduction());
                                                mylist.add(wks);
                                            }

                                            callback.onResult(mylist);
                                        }


                                    }
                                    @Override
                                    public void onReqFailed(String errorMsg) {
                                    }
                                });

                            }


                        }

                        @Override
                        public void onReqFailed(String errorMsg) {




                        }
                    });



                }
            }
        };


        mLiveData = new LivePagedListBuilder(new PageDataSourceFactory(positionalDataSource),
                    new PagedList.Config.Builder().setPageSize(NUM_PER_PAGE)//每次加载的数据数量15
                        //距离本页数据几个时候开始加载下一页数据(例如现在加载10个数据,设置prefetchDistance为2,则滑到第八个数据时候开始加载下一页数据)
                        .setPrefetchDistance(NUM_PER_PAGE)
                        .setEnablePlaceholders(false).setInitialLoadSizeHint(NUM_PER_PAGE).build()).build();


    }



//    public void getOnlineData(String cage){
//        map.put("category",cage);
//
//        Log.d("shows", cage);
//        RequestManager requestManager = RequestManager.getInstance(getApplication());
//        requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
//            @Override
//            public void onReqSuccess(Object result) {
//
//                Gson gson = new Gson();
//                getbacklist = gson.fromJson(String.valueOf(result), Get_MobileBook_GetBooks.class);
//                //Log.d("details", "onReqSuccess: "+getbacklist.dataList.get(0).getTitle());
//
//                for (int i = 0;i<getbacklist.dataList.size();i++){
//                    getbooksdetails(getbacklist.dataList.get(i).getId(),i);
//                }
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//        });
//
//    }
//
//    private void getbooksdetails(String booksid,int nums){
//
//        //Log.d("booksid", "getbooksdetails: "+booksid);
//
//        bookdetailsmap.put("bookId",booksid);
//        RequestManager requestManagers = new RequestManager(getApplication());
//        requestManagers.requestAsyn("mobileBook/infoBook", 0, bookdetailsmap, new RequestManager.ReqCallBack<Object>() {
//            @Override
//            public void onReqSuccess(Object result) {
//              //Log.d("shows", "onReqSuccess: "+String.valueOf(result));
//
//              Gson gson = new Gson();
//              getdetails[nums] = gson.fromJson(String.valueOf(result),Get_MobleBooks_Getdetails.class);
//
//                Log.d("数字", "onReqSuccess: "+nums);
//
//                lognum++;
//
//
//
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//
//        });
//
//    }
//
//



}
