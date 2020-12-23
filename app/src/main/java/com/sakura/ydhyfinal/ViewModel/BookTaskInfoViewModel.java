package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_Booksinfo;
import com.sakura.ydhyfinal.gsonres.Get_TaskInfo;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;

public class BookTaskInfoViewModel extends AndroidViewModel {
    public BookTaskInfoViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Integer> isgetData;

    private Get_TaskInfo getTaskInfo;

    public Get_TaskInfo getGetTaskInfo() {
        return getTaskInfo;
    }

    public MutableLiveData<Integer> getIsgetData() {
        if(isgetData == null){
            isgetData = new MutableLiveData<>();
            isgetData.setValue(0);
        }
        return isgetData;
    }

    public void getOnlineTaskinfo(String taskId, String userId){

        HashMap map = new HashMap();
        map.put("userType","user_type_student");
        map.put("userId",userId);
        map.put("taskId",taskId);

        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileTask/taskDetail", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                getTaskInfo = gson.fromJson(String.valueOf(result),Get_TaskInfo.class);


                isgetData.setValue(1);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });


    }

}
