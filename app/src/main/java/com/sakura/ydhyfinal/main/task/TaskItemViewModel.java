package com.sakura.ydhyfinal.main.task;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.gsonres.Get_MobleTask_MyTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TaskItemViewModel extends ViewModel {

    protected static String URL = "mobileTask/myTask?userId=F2F9105E-B6F8-C2A2-279A-A9DF84701F57&userType=user_type_student";

    protected Get_MobleTask_MyTask[] gtbacklist = new Get_MobleTask_MyTask[3];


    protected void getOnlineTask(String type, Callback callback){

        Log.d("tapy", "getOnlineTask: ");

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Glable.GLABLEURL+URL+type).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);




    }

    protected void processData(String json,int pos){

        Gson gson = new Gson();

        gtbacklist[pos] = gson.fromJson(json,Get_MobleTask_MyTask.class);

        Log.d("mytask", "processData: "+gtbacklist[pos].getDataList().size());

    }



}
