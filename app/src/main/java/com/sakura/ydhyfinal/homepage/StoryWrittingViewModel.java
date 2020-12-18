package com.sakura.ydhyfinal.homepage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.DataSourse.PageDataQuestionFactory;
import com.sakura.ydhyfinal.bean.StoryQues;
import com.sakura.ydhyfinal.gsonres.Get_StoryQues;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StoryWrittingViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel


    public StoryWrittingViewModel(@NonNull Application application) {
        super(application);
    }

    public Get_StoryQues[] get_storyQues = new Get_StoryQues[2];
    //创建一个livedata获取当前页面position
    private MutableLiveData<Integer> pos;
    public MutableLiveData<Integer> getPos() {
        if (pos == null){
            pos = new MutableLiveData<>();
            pos.setValue(0);
        }
        return pos;
    }

    //每页需要加载的数量
    private static final int NUM_PER_PAGE = 9;
    //第一页
    private static final int PAGE_FIRST = 1;

    //当前页码数
    public int mPage = PAGE_FIRST;

    int totalnum = 0;

    //存储List
    public LiveData mLivedata;

    private ArrayList<StoryQues> list = new ArrayList<>();


    public LiveData<PagedList<StoryQues>> getmLivedata() {
        initPageListLive();

        return mLivedata;
    }


    private MutableLiveData<Integer> judes;

    public MutableLiveData<Integer> getJudes() {
        if(judes == null){
            judes = new MutableLiveData<>();
            judes.setValue(0);
        }
        return judes;
    }




    private MutableLiveData<String> pagecage;

    public MutableLiveData<String> getPagecage() {
        if(pagecage == null){
            pagecage = new MutableLiveData<>();
            pagecage.setValue("");
        }

        return pagecage;
    }

    final public PositionalDataSource<StoryQues> positionalDataSource = new PositionalDataSource<StoryQues>() {
        /**
         * recyclerView第一次加载时自动调用
         * @param params 包含当前加载的位置position、下一页加载的长度count
         * @param callback 将数据回调给UI界面使用callback.onResult
         */

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<StoryQues> callback) {
            final int position = computeInitialLoadPosition(params,NUM_PER_PAGE);
            list = new ArrayList<>();


            HashMap maps = new HashMap();
            maps.put("type",pagecage.getValue());
            maps.put("page",String.valueOf(PAGE_FIRST));

            RequestManager requestManager = new RequestManager(getApplication());
            requestManager.requestAsyn("mobileqa/index", 0, maps, new RequestManager.ReqCallBack<Object>() {
                @Override
                public void onReqSuccess(Object result) {
                    Gson gson = new Gson();
                    get_storyQues[pos.getValue()] = gson.fromJson(String.valueOf(result),Get_StoryQues.class);

                    for(int i = 0;i<get_storyQues[pos.getValue()].getDataList().size();i++){
                        StoryQues storyQues = new StoryQues();
                        storyQues.setQuestionId(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionId());
                        storyQues.setQuestionDescription(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionDescription());
                        storyQues.setStudentName(get_storyQues[pos.getValue()].getDataList().get(i).getStudentName());
                        storyQues.setStudentId(get_storyQues[pos.getValue()].getDataList().get(i).getStudentId());
                        storyQues.setQuestionTime(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionTime());
                        list.add(storyQues);
                    }
                    judes.setValue(1);
                    totalnum = get_storyQues[pos.getValue()].getTotal();
                    callback.onResult(list,position);


                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            });
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<StoryQues> callback) {
            if(mPage<totalnum){
                list = new ArrayList<>();
                mPage++;
                HashMap maps = new HashMap();
                maps.put("type",pagecage.getValue());
                maps.put("page",String.valueOf(mPage));

                RequestManager requestManager = RequestManager.getInstance(getApplication());
                requestManager.requestAsyn("mobileqa/index", 0, maps, new RequestManager.ReqCallBack<Object>() {
                    @Override
                    public void onReqSuccess(Object result) {

                        Gson gson = new Gson();
                        get_storyQues[pos.getValue()] = gson.fromJson(String.valueOf(result),Get_StoryQues.class);

                        list = new ArrayList<>();
                        StoryQues storyQues;
                        for (int i = 0;i<get_storyQues[pos.getValue()].getDataList().size();i++){

                            storyQues = new StoryQues();
                            storyQues.setQuestionId(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionId());
                            storyQues.setQuestionDescription(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionDescription());
                            storyQues.setStudentName(get_storyQues[pos.getValue()].getDataList().get(i).getStudentName());
                            storyQues.setStudentId(get_storyQues[pos.getValue()].getDataList().get(i).getStudentId());
                            storyQues.setQuestionTime(get_storyQues[pos.getValue()].getDataList().get(i).getQuestionTime());
                            list.add(storyQues);
                        }
                        callback.onResult(list);


                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });
            }


        }
    };

    public void initPageListLive(){



        mLivedata = new LivePagedListBuilder(new PageDataQuestionFactory(positionalDataSource),
                new PagedList.Config.Builder().
                        setPageSize(10)//每次加载的数据数量9
                        //距离本页数据几个时候开始加载下一页数据(例如现在加载10个数据,设置prefetchDistance为2,则滑到第八个数据时候开始加载下一页数据)
                        .setPrefetchDistance(NUM_PER_PAGE)
                        .setEnablePlaceholders(false).
                        setInitialLoadSizeHint(10).build()).build();





    }


    public void reflashview(){


    }





}