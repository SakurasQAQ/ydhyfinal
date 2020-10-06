package com.sakura.ydhyfinal.main.work;

import android.app.Application;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.DataSourse.PageDataSourceFactory;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.gsonres.Get_MobleBlocks_Booksblocks;

import com.sakura.ydhyfinal.utils.RequestManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkItemViewModel extends AndroidViewModel {

    protected final static String URL = "mobileForum/getBlocks?category=";
    //protected final static String URLS = "/mobileForum/blockDetail?category=";

    protected Get_MobleBlocks_Booksblocks.Get_MobleBlocks_Booksblocks_data[] getbacklist = new Get_MobleBlocks_Booksblocks.Get_MobleBlocks_Booksblocks_data[10];


    //每页需要加载的数量
    private static final int NUM_PER_PAGE = 9;
    //第一页
    private static final int PAGE_FIRST = 1;
    //当前页码数
    private int mPage = PAGE_FIRST;

    int totalnum;

    public WorkItemViewModel(@NonNull Application application) {
        super(application);
    }

    private ArrayList<MyWorks> works = new ArrayList<>();

    //创建一个livedata获取fragment类型
    private MutableLiveData<String> pagecage;

    public MutableLiveData<String> getPagecage() {
        if(pagecage == null){
            pagecage = new MutableLiveData<>();
            pagecage.setValue("");
        }

        return pagecage;
    }

    //创建一个livedata获取当前页面position
    private MutableLiveData<Integer> pos;

    public MutableLiveData<Integer> getPos() {
        if (pos == null){
            pos = new MutableLiveData<>();
            pos.setValue(0);
        }
        return pos;
    }

    public LiveData mLivedata;

    public LiveData<PagedList<MyWorks>> getmLivedata() {
        initPageListLive();

        return mLivedata;
    }


    public void initPageListLive(){
        final PositionalDataSource<MyWorks> positionalDataSource = new PositionalDataSource<MyWorks>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<MyWorks> callback) {
                /**
                 * recyclerView第一次加载时自动调用
                 * @param params 包含当前加载的位置position、下一页加载的长度count
                 * @param callback 将数据回调给UI界面使用callback.onResult
                 */
                final int position = computeInitialLoadPosition(params,NUM_PER_PAGE);
                HashMap maps = new HashMap();
                Log.d("Woks::", "loadInitial: " + 1);
                maps.put("category",pagecage.getValue());
                maps.put("pageNum",String.valueOf(PAGE_FIRST));

                RequestManager requestManager = new RequestManager(getApplication());
                requestManager.requestAsyn("mobileForum/getBlocks", 0, maps, new RequestManager.ReqCallBack<Object>() {
                    @Override
                    public void onReqSuccess(Object result) {
                        Gson gson = new Gson();
                        getbacklist[pos.getValue()] = gson.fromJson(String.valueOf(result), Get_MobleBlocks_Booksblocks.Get_MobleBlocks_Booksblocks_data.class);
                        for(int i=0;i<getbacklist[pos.getValue()].getDataList().size();i++){
                            MyWorks work = new MyWorks();
                            work.setWorksName(getbacklist[pos.getValue()].getDataList().get(i).getTitle());
                            work.setWorksId(getbacklist[pos.getValue()].getDataList().get(i).getId());
                            work.setWorksPicUrl(getbacklist[pos.getValue()].getDataList().get(i).getImg());
                            work.setPostNum(getbacklist[pos.getValue()].getDataList().get(i).getPostNum());
                            work.setThumbNumbers(getbacklist[pos.getValue()].getDataList().get(i).getThumbNumbers());
                            works.add(work);
                        }
                        totalnum = getbacklist[pos.getValue()].getTotalPage();
                        callback.onResult(works,position);
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });


            }


            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<MyWorks> callback) {
                //每次加载到分页条目时页数变为下一页
                if (mPage < totalnum) {
                    mPage++;
                    Log.d("Woks::", "loadInitial: " + (mPage) + "   " + totalnum);
                    //请求数据并解析
                    HashMap maps = new HashMap();
                    maps.put("category", pagecage.getValue());
                    maps.put("pageNum", String.valueOf(mPage));

                    RequestManager requestManager = new RequestManager(getApplication());
                    requestManager.requestAsyn("mobileForum/getBlocks", 0, maps, new RequestManager.ReqCallBack<Object>() {
                        @Override
                        public void onReqSuccess(Object result) {

                            Gson gson = new Gson();
                            getbacklist[pos.getValue()] = gson.fromJson(String.valueOf(result), Get_MobleBlocks_Booksblocks.Get_MobleBlocks_Booksblocks_data.class);

                            //                        Log.d("showdata", "onReqSuccess: "+getbacklistmore[pos.getValue()].getCurrentPage());

                            works = new ArrayList<>();
                            MyWorks work;
                            for (int i = 0; i < getbacklist[pos.getValue()].getDataList().size(); i++) {

                                work = new MyWorks();
                                work.setWorksName(getbacklist[pos.getValue()].getDataList().get(i).getTitle());
                                work.setWorksId(getbacklist[pos.getValue()].getDataList().get(i).getId());
                                work.setWorksPicUrl(getbacklist[pos.getValue()].getDataList().get(i).getImg());
                                work.setPostNum(getbacklist[pos.getValue()].getDataList().get(i).getPostNum());
                                work.setThumbNumbers(getbacklist[pos.getValue()].getDataList().get(i).getThumbNumbers());
                                works.add(work);
                            }
                            callback.onResult(works);
                        }
                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    });
                }


            }
        };

        mLivedata = new LivePagedListBuilder(new PageDataSourceFactory(positionalDataSource),
                    new PagedList.Config.Builder().
                            setPageSize(9)//每次加载的数据数量9
                        //距离本页数据几个时候开始加载下一页数据(例如现在加载10个数据,设置prefetchDistance为2,则滑到第八个数据时候开始加载下一页数据)
                        .setPrefetchDistance(NUM_PER_PAGE)
                        .setEnablePlaceholders(false).setInitialLoadSizeHint(9).build()).build();




    }










}
