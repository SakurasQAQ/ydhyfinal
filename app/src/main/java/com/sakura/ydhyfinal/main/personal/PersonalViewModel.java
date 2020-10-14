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

    public MutableLiveData<Boolean> getIsLogin() {
        if(isLogin == null){
            isLogin = new MutableLiveData<>();
            isLogin.setValue(false);
        }

        return isLogin;
    }

    public MutableLiveData<String> username;

    public MutableLiveData<String> getUsername() {
        if(username == null){
            username = new MutableLiveData<>();
            username.setValue("未登陆");
        }

        return username;
    }

    public MutableLiveData<String> userschool;

    public MutableLiveData<String> getUserschool() {
        if(userschool == null){
            userschool = new MutableLiveData<>();
            userschool.setValue("请点击头像进行登陆");
        }

        return userschool;
    }

    public MutableLiveData<String> upoint;

    public MutableLiveData<String> getUpoint() {
        if(upoint == null){
            upoint = new MutableLiveData<>();
            upoint.setValue("0");
        }
        return upoint;
    }

    public MutableLiveData<String> ulevel;

    public MutableLiveData<String> getUlevel() {
        if(ulevel == null){
            ulevel = new MutableLiveData<>();
            ulevel.setValue("0");
        }
        return ulevel;
    }

    public MutableLiveData<String> ucheng;

    public MutableLiveData<String> getUcheng() {
        if(ucheng == null){
            ucheng = new MutableLiveData<>();
            ucheng.setValue("无");
        }
        return ucheng;
    }

    //public MutableLiveData<Boolean> ;

    public PersonalViewModel(@NonNull Application application) {
        super(application);
    }




    protected void loginState(){

        SharedPreferences userid = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);

        Log.d("showmyinfo", "loginState: "+userid.getString("userId","")+"islogin"+userid.getBoolean("islogin",true));

        if(!isLogin.getValue()){
            if(userid.getBoolean("isLogin",false)){
                setUIdata();
                isLogin.setValue(true);
            }else {

                Logout();
            }
        }




    }

    private void setUIdata(){

        SharedPreferences user = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);


        username.setValue(user.getString("usertName",""));
        userschool.setValue(user.getString("schoolName",""));
        upoint.setValue(user.getString("userPoints",""));
        ulevel.setValue(user.getString("rank",""));
        ucheng.setValue(user.getString("ranktit",""));

    }






    protected void Logout(){
        isLogin.setValue(false);

        //清除缓存内容
        SharedPreferences.Editor userEdit = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).edit();

        userEdit.clear();

//        userEdit.putBoolean("isLogin",false);
//        userEdit.remove("usertName");
//        userEdit.remove("userId");
//        userEdit.remove("schoolName");
//        userEdit.remove("className");
//        userEdit.remove("grade");
//        userEdit.remove("userimg");
//        userEdit.remove("isLogin");


        userEdit.apply();

        username.setValue("未登录");
        userschool.setValue("请点击头像进行登陆");
        upoint.setValue("0");
        ulevel.setValue("0");
        ucheng.setValue("0");

    }









}
