package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.bean.JsonBean;
import com.sakura.ydhyfinal.gsonres.Get_UserId;
import com.sakura.ydhyfinal.gsonres.Get_UserInfo;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sakura.ydhyfinal.utils.JsonUtils.*;

public class LoginViewModel extends AndroidViewModel {

    //public boolean loginsuccess;

    protected Get_UserId getbackUserid;
    public List<JsonBean> options1Items = new ArrayList<>();
    public ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private HashMap maps = new HashMap<>();
    private Get_UserInfo getUserInfo;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Integer> loginState;

    public MutableLiveData<Integer> getLoginState() {
        if (loginState == null) {
            loginState = new MutableLiveData<>();
            loginState.setValue(0);
        }
        return loginState;
    }


    public void userLogin(String username,String password,String usertype,String schoolId)
    {
        //无实际作用的经纬度，占位传递数据
        String lag="1",lng="1";


        maps.put("userName","学生甲");
        //maps.put("userName",username);
        maps.put("password",password);
        maps.put("schoolId",schoolId);
        maps.put("userType",usertype);
        maps.put("lat",lag);
        maps.put("lng",lng);

        Log.d("查看数据：", "userLogin: "+username+"+"+password+"+"+String.valueOf(schoolId)+"+"+usertype+"+"+lag);


        //表单发送请求
        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileUser/login", 2, maps, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                //Log.d("测试结果", "onReqSuccess: "+result);
                //解析json数据
                getUserId(String.valueOf(result),username,schoolId,password);



            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toast.makeText(getApplication(),"密码有误，请重新输入",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void releaseData(){
        String JsonData = getJson(getApplication(), "citys.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            options2Items.add(cityList);
            options3Items.add(province_AreaList);
        }
    }

    private void getUserId(String json,String username,String schoolId,String pwd){

        Gson gson = new Gson();

        getbackUserid = gson.fromJson(json,Get_UserId.class);

        //个人数据再获取
        getUserInfo(getbackUserid.getUserId(),schoolId);




        //将获取到的Userid存入缓存中
        SharedPreferences.Editor userEdit = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        userEdit.putString("usertName", username);
        userEdit.putString("userPwd",pwd);
        userEdit.putString("userId",getbackUserid.getUserId());
        userEdit.putBoolean("isLogin", true);
        userEdit.apply();

    }

    private void getUserInfo(String uid,String schoolId){

        HashMap mapuser = new HashMap();

        mapuser.put("userId",uid);
        mapuser.put("userType","user_type_student");

        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("mobileUser/userInfo", 0, mapuser, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                reaseInfo(String.valueOf(result),schoolId);

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });





    }

    private void reaseInfo(String json,String schoolId){

        Gson gson = new Gson();
        getUserInfo = gson.fromJson(json,Get_UserInfo.class);

        Log.d("USer:", "getUserId: -------------"+getUserInfo.getGrade());

        //将获取到的个人信息存入缓存中

        SharedPreferences.Editor userinfoediter = getApplication().getSharedPreferences("user",Context.MODE_PRIVATE).edit();
        userinfoediter.putString("idCard",getUserInfo.getIdCard());
        userinfoediter.putString("gender",getUserInfo.getGender());

        userinfoediter.putString("schoolId",schoolId);

        userinfoediter.putString("className",getUserInfo.getClassName());
        userinfoediter.putString("userimg",getUserInfo.getAvatar());
        userinfoediter.putString("ranktit",getUserInfo.getRankTitle());
        userinfoediter.putString("classId",getUserInfo.getClassId());
        userinfoediter.putString("userPoints",getUserInfo.getUserPoints());
        userinfoediter.putString("grade",getUserInfo.getGrade());
        userinfoediter.putString("rank",getUserInfo.getRank());
        userinfoediter.putString("schoolName",getUserInfo.getSchoolname());
        userinfoediter.apply();


        loginState.setValue(1);
    }








}
