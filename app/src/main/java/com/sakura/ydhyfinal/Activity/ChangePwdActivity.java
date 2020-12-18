package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.sakura.ydhyfinal.ForgetPwdActivity;
import com.sakura.ydhyfinal.LoginActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.gsonres.Get_MSGSP;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ChangePwdActivity extends AppCompatActivity {

    private TextView oldPwd,newPwd,checkNewPwd;

    private Button btnSub;

    private LinearLayout btnBack;

    private Get_MSGSP msgsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));


        String userId = getIntent().getStringExtra("Uid");

        oldPwd = findViewById(R.id.changespwd_pwdyuan);
        newPwd = findViewById(R.id.changespwd_pwdnew);
        checkNewPwd = findViewById(R.id.changespwd_pwdchk);

        btnSub = findViewById(R.id.changespwd_btnsub);

        btnBack = findViewById(R.id.changespwd_btnback);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        btnSub.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {

                if(oldPwd.getText().toString().equals("") || newPwd.getText().toString().equals("") || checkNewPwd.getText().toString().equals("")){
                    Toasty.custom(ChangePwdActivity.this,"请将信息输入完整",getResources().getDrawable(R.drawable.ic_jingfish),getResources().getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();

                }else {
                    if(!newPwd.getText().toString().equals(checkNewPwd.getText().toString())){
                        Toasty.custom(ChangePwdActivity.this,"两次输入的密码不一致",getResources().getDrawable(R.drawable.ic_jingfish),getResources().getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();

                    }else {
                        SubOnline(userId,oldPwd.getText().toString(),checkNewPwd.getText().toString());
                    }
                }

            }
        });

    }

    public void SubOnline(String userid,String oldpwd,String newPwd){

        HashMap map = new HashMap();
        map.put("userId",userid);
        map.put("originalPwd",oldpwd);
        map.put("newPwd",newPwd);

        RequestManager requestManager = RequestManager.getInstance(this);
        requestManager.requestAsyn("mobileUser/updatePwd", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                Gson gson = new Gson();
                msgsp = gson.fromJson(String.valueOf(result),Get_MSGSP.class);
                if(!msgsp.getMessage().equals("")){
                    Toasty.custom(getApplicationContext(),"密码修改成功",getDrawable(R.drawable.ic_trueicon),getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,true,true).show();

                    startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class));
                    //清除缓存内容
                    SharedPreferences.Editor userEdit = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE).edit();

                    userEdit.clear();

                    finish();

                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toasty.custom(getApplicationContext(),"原密码错误或服务器未响应",getDrawable(R.drawable.ic_trueicon),getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,false,true).show();
            }
        });
    }

}