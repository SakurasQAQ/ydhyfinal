package com.sakura.ydhyfinal.main.personal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_UserInfo;
import com.sakura.ydhyfinal.utils.MyApplication;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;

public class PersonalViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel


    public boolean isLoginSuccess = false;


    public MutableLiveData<Boolean> isLogin;
    //public MutableLiveData<Boolean> ;

    public PersonalViewModel(@NonNull Application application) {
        super(application);
    }




    protected void loginState(){

        SharedPreferences userid = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);

        Log.d("showmyinfo", "loginState: "+userid.getString("userId","")+"islogin"+userid.getBoolean("islogin",true));

        if(userid.getBoolean("islogin",true)){
            isLoginSuccess = true;

        }else{
            isLoginSuccess = false;
        }


    }





    protected void Logout(){



        //清除缓存内容
        SharedPreferences.Editor userEdit = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).edit();

        userEdit.putBoolean("isLogin",false);
        userEdit.remove("userName");
        userEdit.remove("userId");


        userEdit.apply();


    }









}
