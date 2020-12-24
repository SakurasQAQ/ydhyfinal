package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.DataSourse.PageDataQuestionFactory;
import com.sakura.ydhyfinal.DataSourse.PageDateTaskBooksFactory;
import com.sakura.ydhyfinal.bean.MyTask;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.bean.TaskBooks;
import com.sakura.ydhyfinal.gsonres.Get_MyReading;
import com.sakura.ydhyfinal.gsonres.Get_TaskInfo;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadingbooksViewModel extends AndroidViewModel {

    private Get_MyReading myReading;

    public ReadingbooksViewModel(@NonNull Application application) {
        super(application);
    }


    //每页需要加载的数量
    private static final int NUM_PER_PAGE = 9;
    //第一页
    private static final int PAGE_FIRST = 1;
    //当前页码数
    private int mPage = PAGE_FIRST;

    int totalnum;

    private MutableLiveData<Integer> judes;

    public MutableLiveData<Integer> getJudes() {
        if(judes == null){
            judes = new MutableLiveData<>();
            judes.setValue(0);
        }
        return judes;
    }



    private ArrayList<TaskBooks> taskBooks = new ArrayList<>();

    //创建一个livedata获取fragment类型
    private MutableLiveData<String> pagecage;

    public MutableLiveData<String> getPagecage() {
        if(pagecage == null){
            pagecage = new MutableLiveData<>();
            pagecage.setValue("");
        }

        return pagecage;
    }


    public LiveData mLivedata;

    public LiveData<PagedList<TaskBooks>> getmLivedata() {
        initPageListLive();

        return mLivedata;
    }

    private void initPageListLive(){
        final PositionalDataSource<TaskBooks> positionalDataSource = new PositionalDataSource<TaskBooks>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<TaskBooks> callback) {
                taskBooks = new ArrayList<>();
                final int position = computeInitialLoadPosition(params,NUM_PER_PAGE);

                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
                String userid = sharedPreferences.getString("userId","");

                HashMap map = new HashMap();
                map.put("type",pagecage.getValue());
                map.put("userId",userid);
                map.put("pageNum",String.valueOf(PAGE_FIRST));
                RequestManager requestManager = RequestManager.getInstance(getApplication());
                requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
                    @Override
                    public void onReqSuccess(Object result) {

                        Gson gson = new Gson();
                        myReading = gson.fromJson(String.valueOf(result),Get_MyReading.class);

                        for(int i = 0;i<myReading.getDataList().size();i++){
                            TaskBooks task = new TaskBooks();
                            task.setId(myReading.getDataList().get(i).getId());
                            task.setCoverImg(myReading.getDataList().get(i).getCoverImg());
                            task.setTitle(myReading.getDataList().get(i).getTitle());

                            taskBooks.add(task);
                        }
                        judes.setValue(1);
                        totalnum = myReading.getTotalPage();
                        callback.onResult(taskBooks,position);


                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<TaskBooks> callback) {
                if(mPage < totalnum){
                    mPage++;
                    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
                    String userid = sharedPreferences.getString("userId","");
                    HashMap map = new HashMap();
                    map.put("type",pagecage.getValue());
                    map.put("userId",userid);
                    map.put("pageNum",String.valueOf(mPage));

                    RequestManager requestManager = RequestManager.getInstance(getApplication());
                    requestManager.requestAsyn("mobileBook/getBooks", 0, map, new RequestManager.ReqCallBack<Object>() {
                        @Override
                        public void onReqSuccess(Object result) {

                            Gson gson = new Gson();
                            myReading = gson.fromJson(String.valueOf(result),Get_MyReading.class);
                            taskBooks = new ArrayList<>();

                            TaskBooks task;
                            for(int i = 0;i<myReading.getDataList().size();i++){
                                task = new TaskBooks();
                                task.setId(myReading.getDataList().get(i).getId());
                                task.setCoverImg(myReading.getDataList().get(i).getCoverImg());
                                task.setTitle(myReading.getDataList().get(i).getTitle());
                                taskBooks.add(task);
                            }

                            callback.onResult(taskBooks);
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    });


                }
            }
        };

        mLivedata = new LivePagedListBuilder(new PageDateTaskBooksFactory(positionalDataSource),
                new PagedList.Config.Builder().
                        setPageSize(9)//每次加载的数据数量9
                        //距离本页数据几个时候开始加载下一页数据(例如现在加载10个数据,设置prefetchDistance为2,则滑到第八个数据时候开始加载下一页数据)
                        .setPrefetchDistance(NUM_PER_PAGE)
                        .setEnablePlaceholders(false).setInitialLoadSizeHint(9).build()).build();

    }







}
