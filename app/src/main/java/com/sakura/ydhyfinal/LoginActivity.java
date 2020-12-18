package com.sakura.ydhyfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.Glable.Glable;
import com.sakura.ydhyfinal.ViewModel.LoginViewModel;
import com.sakura.ydhyfinal.databinding.ActivityLoginBinding;
import com.sakura.ydhyfinal.utils.MyApplication;

import es.dmoral.toasty.Toasty;

import static com.sakura.ydhyfinal.utils.JsonUtils.getJson;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    LoginViewModel myLoginViewmodel;

    private int flag = 0;
    private String utype = Glable.GLABLE_STUDENT_TYPE;



    int Schoolid=0;

    boolean addjude;

    private Dialog dialog;


    //点击事件
    private View.OnClickListener loginListener = v -> {
        switch (v.getId()){
            case R.id.login_btn_login:

                //判断是否选择学校
                if(Schoolid==0){
                    //Toast.makeText(getApplication(),"请先选择学校",Toast.LENGTH_SHORT).show();
                    Toasty.warning(getApplication(), "请先选择学校", Toast.LENGTH_SHORT, true).show();
                }else if (Schoolid == 1){
                    Toasty.warning(getApplication(), "当前地区学校不存在", Toast.LENGTH_SHORT, true).show();

                }else{
                    String username = String.valueOf(binding.loginEditUsername.getText());
                    String password = String.valueOf(binding.loginEditPassword.getText());

                    new Handler().postDelayed(() -> {
                        myLoginViewmodel.userLogin(username,password,utype,String.valueOf(Schoolid));
                    }, 1000);
                }


                break;
            case R.id.ll_position:
                showPickerView();
                break;

            case R.id.login_forgetPwd:
                startActivity(new Intent(getApplicationContext(),ForgetPwdActivity.class));
                break;


            case R.id.login_btn_back:
                this.finish();
                break;
        }
    };


    //监听文本事件
    private TextWatcher Txtwatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!TextUtils.isEmpty(binding.loginEditUsername.getText().toString().trim()) && !TextUtils.isEmpty(binding.loginEditPassword.getText().toString().trim())){

                couldLogin(true);
            }else{
                couldLogin(false);
            }
        }
    };




@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);


        myLoginViewmodel = new ViewModelProvider(this).get(LoginViewModel.class);


        //判断上次是否有点击记住我

        SharedPreferences rmbEdit = getApplication().getSharedPreferences("UserSave", Context.MODE_PRIVATE);

        if(rmbEdit.getBoolean("Remember",true)){

            binding.loginEditUsername.setText(rmbEdit.getString("username",""));
            binding.loginEditPassword.setText(rmbEdit.getString("userpwd",""));

            



        }


        //点击切换用户事件
        binding.loginImgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){
                    binding.loginImgChange.setImageResource(R.drawable.login_xuesheng);
                    binding.loginPersonalcg.setText("我是学生");
                    flag = 1;
                    utype = Glable.GLABLE_STUDENT_TYPE;
                }else{
                    binding.loginImgChange.setImageResource(R.drawable.login_jiaoshi);
                    binding.loginPersonalcg.setText("我是老师");
                    flag = 0;
                    utype = Glable.GLABLE_TEACHER_TYPE;
                }
            }
        });





        init();
        listeners();

         //子线程解析省市区数据
        Thread thread = new Thread(() ->  myLoginViewmodel.releaseData());
        thread.start();

        addObserver();

    }

    private void init(){
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        binding.loginBtnLogin.setEnabled(false);
        binding.loginImgChange.setImageResource(R.drawable.login_xuesheng);
        binding.loginPersonalcg.setText("我是学生");
        flag = 1;


    }

    private void listeners(){

        binding.loginBtnLogin.setOnClickListener(loginListener);
        binding.llPosition.setOnClickListener(loginListener);
        binding.loginBtnBack.setOnClickListener(loginListener);

        binding.loginEditUsername.addTextChangedListener(Txtwatcher);
        binding.loginEditPassword.addTextChangedListener(Txtwatcher);

        binding.loginForgetPwd.setOnClickListener(loginListener);
    }



    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String opt1tx =  myLoginViewmodel.options1Items.size() > 0 ?
                    myLoginViewmodel.options1Items.get(options1).getPickerViewText() : "";

            String opt2tx =  myLoginViewmodel.options2Items.size() > 0
                    &&  myLoginViewmodel.options2Items.get(options1).size() > 0 ?
                    myLoginViewmodel.options2Items.get(options1).get(options2) : "";

            String opt3tx = myLoginViewmodel.options2Items.size() > 0
                    &&  myLoginViewmodel.options3Items.get(options1).size() > 0
                    &&  myLoginViewmodel.options3Items.get(options1).get(options2).size() > 0 ?
                    myLoginViewmodel.options3Items.get(options1).get(options2).get(options3) : "";

            String tx = opt1tx + opt2tx + opt3tx;
            binding.tvLocation.setText(opt3tx);

            binding.tvLocation.setTextColor(getResources().getColor(R.color.white));

            switch (tx) {
                case "广东省珠海市阅读海洋小学":
                    //myLoginViewmodel.orgId.setValue(246001);
                    Schoolid = 1000000;
                    break;
                case "广东省珠海市北京师范大学珠海分校":
                    Schoolid = 4404005;
                    //myLoginViewmodel.orgId.setValue(246002);
                    break;
                case "广东省珠海市香洲第一小学":
                    Schoolid = 4404001;
                    //myLoginViewmodel.orgId.setValue(246003);
                    break;
                case "广东省珠海市景园小学":
                    Schoolid = 4404002;
                    break;
                case "广东省珠海市金湾小学":
                    Schoolid = 4404004;
                    break;
                case "广东省珠海市香洲十小":
                    Schoolid = 4404006;
                    break;
                case "广东省珠海市金湾区航空新城小学":
                    Schoolid = 4404007;
                    break;
                case "广东省深圳市宝安区坪洲小学":
                    Schoolid = 4403001;
                    break;

                default:
                    Schoolid = 1;
                    //myLoginViewmodel.orgId.setValue(0);
                    break;
            }
            //myLoginViewmodel.orgName = tx;
        })

                .setTitleText("选择你的学校")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(Color.parseColor("#FC2D55AB"))
                .setCancelColor(Color.GRAY)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(myLoginViewmodel.options1Items, myLoginViewmodel.options2Items, myLoginViewmodel.options3Items);//三级选择器
        pvOptions.show();
    }

    private void couldLogin(boolean judge)
    {
        if(judge){
            binding.loginBtnLogin.setEnabled(true);
        }else{
            binding.loginBtnLogin.setEnabled(false);
        }
    }

    private void addObserver(){
        myLoginViewmodel.getLoginState().observe(this,integer -> {

            switch (integer){
                case 1:

                    //判断是否记住我
                    SharedPreferences.Editor rmbEdit = getApplication().getSharedPreferences("UserSave", Context.MODE_PRIVATE).edit();
                    if(binding.loginCheckRemember.isChecked()){
                        rmbEdit.putBoolean("Remember",true);
                        rmbEdit.putString("username",String.valueOf(binding.loginEditUsername.getText()));
                        rmbEdit.putString("userpwd",String.valueOf(binding.loginEditPassword.getText()));



                    }else{
                        rmbEdit.putBoolean("Remember", false);
                        rmbEdit.remove("username");
                        rmbEdit.remove("userpwd");


                    }
                    rmbEdit.apply();
                    startActivity(new Intent(getApplicationContext(),BottomNavActivity.class));

                    Toasty.custom(getApplicationContext(),"登陆成功！",getDrawable(R.drawable.ic_trueicon),getColor(R.color.dodgerblue),getColor(R.color.white),Toasty.LENGTH_SHORT,true,true).show();
                    //Toasty.success(getApplicationContext(), "登陆成功!", Toast.LENGTH_SHORT, true).show();
                    finish();
            }

        });

    }



}

