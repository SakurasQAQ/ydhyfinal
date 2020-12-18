package com.sakura.ydhyfinal.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.AnswerQuestionAdapter;
import com.sakura.ydhyfinal.adapter.QuesAnswerAdapter;
import com.sakura.ydhyfinal.bean.AnswerBean;
import com.sakura.ydhyfinal.databinding.ActivityStoryWrittingBinding;
import com.sakura.ydhyfinal.databinding.ActivityStoryWrittingInfoBinding;
import com.sakura.ydhyfinal.dialogView.AnimalsDialog;
import com.sakura.ydhyfinal.dialogView.AnimalsDialogMakesure;
import com.sakura.ydhyfinal.utils.ChangeTime;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.util.ArrayList;
import java.util.List;

public class StoryWrittingInfoActivity extends AppCompatActivity {

    ActivityStoryWrittingInfoBinding binding;

    private StoryWrittingInfoViewModel mViewmodel;

    private AnswerQuestionAdapter adapter;


    Handler handler = new Handler();

    private List<AnswerBean> mylist = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
        binding = DataBindingUtil.setContentView(this,R.layout.activity_story_writting_info);


        String a = getIntent().getStringExtra("questionId");
        String b = getIntent().getStringExtra("studentName");
        String c = getIntent().getStringExtra("questionTime");
        String d = getIntent().getStringExtra("questionInfo");

        String uId = getIntent().getStringExtra("StudentId");

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userId","");


        if(userid.equals(uId)){
            //Toast.makeText(this,"是本人发布的问题",Toast.LENGTH_SHORT).show();
            binding.quesInfoDelet.setVisibility(View.VISIBLE);
        }

        c = ChangeTime.format(c);



        binding.quesInfoDescrp.setText(d);
        binding.quesInfoUsername.setText(b);
        binding.quesInfoTime.setText(c);

        mViewmodel = new ViewModelProvider(this).get(StoryWrittingInfoViewModel.class);

        mViewmodel.getAnswerOnline(a);

        binding.quesInfoGetrecylist.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnswerQuestionAdapter(mylist,getApplicationContext());
        binding.quesInfoGetrecylist.setAdapter(adapter);

        mViewmodel.getMyList().observe(this, new Observer<List<AnswerBean>>() {
            @Override
            public void onChanged(List<AnswerBean> list) {

                for(int i =0;i<list.size();i++){
                    mylist.add(list.get(i));
                }

                adapter.notifyDataSetChanged();
            }
        });

        mViewmodel.getExsitList().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    binding.quesInfoGetrecylist.setVisibility(View.GONE);
                    binding.quesInfoNojilu.setVisibility(View.VISIBLE);
                }
            }
        });



        //mViewmodel.getAnswerOnline();
        binding.quesInfoDelet.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {

                AnimalsDialogMakesure dialogMakesure = new AnimalsDialogMakesure(StoryWrittingInfoActivity.this,R.style.Dialog_Msg,"删除回答","确定删除此回答么？");
                dialogMakesure.setMyOnclickListener(new AnimalsDialog.MyOnclickListener() {
                    @Override
                    public void onYesClick() {
                        mViewmodel.questionDelete(uId,a);

                        dialogMakesure.dismiss();



                    }
                });
                dialogMakesure.show();
                //mViewmodel.questionDelete(a,uId);
            }
        });


        mViewmodel.getDeleteJude().observe(StoryWrittingInfoActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("删除成功")){
                    finish();
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





}