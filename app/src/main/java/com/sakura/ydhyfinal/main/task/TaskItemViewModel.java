package com.sakura.ydhyfinal.main.task;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.gsonres.Get_MobleTask_MyTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.sakura.ydhyfinal.utils.MyApplication.getContext;

public class TaskItemViewModel extends AndroidViewModel {

    protected static String URL = "mobileTask/myTask?userType=user_type_student&userId=";

    protected Get_MobleTask_MyTask[] gtbacklist = new Get_MobleTask_MyTask[3];

    //获取当前userid
    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
    String usersId = sharedPreferences.getString("userId","");

    public TaskItemViewModel(@NonNull Application application) {
        super(application);
    }

    protected void getOnlineTask(String type, Callback callback){

        Log.d("tapy", "getOnlineTask: ");

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Glable.GLABLEURL+URL+usersId+type).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);




    }

    protected void processData(String json,int pos){

        Gson gson = new Gson();

        gtbacklist[pos] = gson.fromJson(json,Get_MobleTask_MyTask.class);

        Log.d("mytask", "processData: "+gtbacklist[pos].getDataList().size());

    }



}
