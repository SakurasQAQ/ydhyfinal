package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;

public class PersonalInfoActivity extends AppCompatActivity {

    private ImageView user_img,checkPwd,changePWd;

    private TextView TexName,TexSex,TexSchool,TitSchool,Texid,TexClass,TexCheng,TexPwd;

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        user_img = findViewById(R.id.personal_img);
        TexName = findViewById(R.id.personal_ink_name);
        TexSex = findViewById(R.id.personal_ink_sex);
        TexSchool = findViewById(R.id.personal_ink_school);
        TitSchool = findViewById(R.id.personal_schooltxt);
        Texid  = findViewById(R.id.personal_ink_idcard);
        TexClass = findViewById(R.id.personal_ink_class);
        TexCheng = findViewById(R.id.personal_ink_chenghao);
        TexPwd = findViewById(R.id.personal_ink_pwd);
        checkPwd = findViewById(R.id.personal_checkimg);
        toolbar = findViewById(R.id.personals_toolbar);
        changePWd = findViewById(R.id.personal_changePwd);

        //获取个人信息
        SharedPreferences userinfos = this.getSharedPreferences("user", Context.MODE_PRIVATE);

        String url = userinfos.getString("userimg","");
        String name = userinfos.getString("usertName","");
        String sex = userinfos.getString("gender","");
        String schoolName = userinfos.getString("schoolName","");
        String idCard = userinfos.getString("idCard","");
        String classinfo = userinfos.getString("grade","")+"  "+userinfos.getString("className","");
        String ranktit  =userinfos.getString("ranktit","");
        String pwd = userinfos.getString("userPwd","");

        String Userid = userinfos.getString("userId","");


        //赋值
        Glide.with(this).load(url).into(user_img);
        TexName.setText(name);
        TexSex.setText(sex);
        TitSchool.setText(schoolName+"  -学生用户");
        TexSchool.setText(schoolName);
        Texid.setText(idCard);
        TexClass.setText(classinfo);
        TexCheng.setText(ranktit);

        TexPwd.setText(pwd);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        changePWd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Uid",Userid);
                intent.setClass(PersonalInfoActivity.this,ChangePwdActivity.class);
                startActivity(intent);
            }
        });



        //图标查看密码的方法
        checkPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == R.id.personal_checkimg){
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            checkPwd.setImageResource(R.drawable.ic_eyesdown);
                            TexPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            break;

                        case MotionEvent.ACTION_UP:
                            checkPwd.setImageResource(R.drawable.ic_eyes);
                            TexPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            break;
                    }
                }

                return true;
            }
        });




    }
}