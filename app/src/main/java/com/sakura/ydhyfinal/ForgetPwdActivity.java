package com.sakura.ydhyfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.sakura.ydhyfinal.gsonres.Get_MSG;
import com.sakura.ydhyfinal.gsonres.Get_MSGSP;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ForgetPwdActivity extends AppCompatActivity{

    private EditText idnumber,newPwd,newPwdCk,username;
    private LinearLayout lineBack;

    private Button button;

    private Get_MSGSP get_msgsp;


    private View.OnClickListener listener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {

            switch (v.getId()){

                case R.id.forget_btnback:
                    finish();
                    break;

                case R.id.forget_btnsub:

                    if(username.getText().toString().equals("") || newPwd.getText().toString().equals("") || newPwdCk.getText().toString().equals("") || idnumber.getText().toString().equals("")){
                        Toasty.custom(ForgetPwdActivity.this,"请将信息输入完整",getResources().getDrawable(R.drawable.ic_jingfish),getResources().getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();

                    }else {
                        if(!newPwd.getText().toString().equals(newPwdCk.getText().toString())){
                            Toasty.custom(ForgetPwdActivity.this,"两次密码输入不一致",getResources().getDrawable(R.drawable.ic_jingfish),getResources().getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();
                        }else {
                            //Toast.makeText(ForgetPwdActivity.this, "可提交", Toast.LENGTH_SHORT).show();
                            submittoChange(username.getText().toString(),idnumber.getText().toString(),newPwdCk.getText().toString());
                        }


                    }

                    break;
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        lineBack = findViewById(R.id.forget_btnback);

        idnumber = findViewById(R.id.forget_pwd1);
        newPwd = findViewById(R.id.forget_pwd2);
        newPwdCk = findViewById(R.id.forget_pwd3);

        button = findViewById(R.id.forget_btnsub);

        username = findViewById(R.id.forget_pwd0);



        addlistener();



    }


    private void addlistener(){

        lineBack.setOnClickListener(listener);
        button.setOnClickListener(listener);

    }





    private void submittoChange(String username,String idC,String newpwd){

        HashMap hashMap = new HashMap();

        hashMap.put("userName",username);

        hashMap.put("idCard",idC);
        hashMap.put("newPwd",newpwd);




        RequestManager requestManager = RequestManager.getInstance(this);
        requestManager.requestAsyn("mobileUser/retrievePwd", 1, hashMap, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();

                get_msgsp = gson.fromJson(String.valueOf(result),Get_MSGSP.class);


                if(!get_msgsp.getMessage().equals("")){
                    Toasty.custom(getApplicationContext(),"密码修改成功",getDrawable(R.drawable.ic_trueicon),getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,true,true).show();
                    finish();

                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toasty.custom(getApplicationContext(),"信息有误或未找到该身份证号",getDrawable(R.drawable.ic_trueicon),getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();

            }
        });


    }

}