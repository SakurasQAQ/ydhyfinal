package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.AnswerQuesViewModel;
import com.sakura.ydhyfinal.adapter.AnswerPagerAdapter;
import com.sakura.ydhyfinal.databinding.ActivityAnswerQuesBinding;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class AnswerQuesActivity extends AppCompatActivity {

    private ActivityAnswerQuesBinding binding;

    //private ArrayList<String> list = new ArrayList<>();

    private ArrayList<String> arrayList = new ArrayList<>();

    private String[] anserlist;

    private AnswerPagerAdapter adapter;

    private AnswerQuesViewModel mViewModel;

    private Handler mHander=new Handler();
    private int mCount=0;

    private int currentpage = 1;

    private Runnable mCounter=new Runnable() {
        @Override
        public void run() {
            mCount++;
            binding.anserTimecount.setText("当前用时: "+mCount+"秒");
            mHander.postDelayed(this,1000);
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.anser_btn_next:
                    binding.anserCardVp.setCurrentItem(binding.anserCardVp.getCurrentItem()+1,false);
                    currentpage += 1;
                    binding.anserBootomCurrentpage.setText(""+currentpage);

                    btnbeforstyle();
                    btnafterstyle();


                    break;
                case R.id.anser_btn_befor:


                    binding.anserCardVp.setCurrentItem(binding.anserCardVp.getCurrentItem()-1,false);
                    btnbeforstyle();
                    btnafterstyle();
                    currentpage -=1;
                    if(currentpage==0){
                        currentpage = 1;
                    }
                    binding.anserBootomCurrentpage.setText(""+currentpage);

                    break;


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_answer_ques);
        mViewModel = new ViewModelProvider(this).get(AnswerQuesViewModel.class);

        String booksname = getIntent().getStringExtra("bookName");
        String booksId = getIntent().getStringExtra("bookId");
        Log.d("log", "onCreate: "+booksId+booksname);

        mViewModel.getOnlineQuestionAns(booksId);

        binding.anserToptit.setText("《 "+booksname+" 》");

        binding.anserBootomCurrentpage.setText("1");


        mHander.post(mCounter);

        addObservse();
        addlistener();





    }

    private void initView(){ }

    private void addObservse(){

        mViewModel.getMumbers().observe(this,integer -> {
            if (integer == 3){

                binding.anserBootomQuestotal.setText("/"+mViewModel.getAlllist().size());
                anserlist = new String[mViewModel.getAlllist().size()];
                adapter = new AnswerPagerAdapter(this,mViewModel.getAlllist(),anserlist);

                binding.anserCardVp.setAdapter(adapter);

                binding.anserCardVp.setOffscreenPageLimit(mViewModel.getAlllist().size());



                adapter.setAnswerContext(new AnswerPagerAdapter.Ctxanswer() {
                    @Override
                    public void usersAnswer(String[] answer) {
                        for(String a:answer){
                            Log.d("show", "usersAnswer: "+a);
                            System.out.print(a);
                        }
                    }
                });



            }

        });


    }

    private void addlistener(){

        binding.anserBtnNext.setOnClickListener(listener);
        binding.anserBtnBefor.setOnClickListener(listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHander.removeCallbacks(mCounter);
    }

    private void btnbeforstyle(){

        if(binding.anserCardVp.getCurrentItem() == 0){

            binding.anserBtnBefor.setClickable(false);
            binding.anserBtnBefor.setBackground(getResources().getDrawable(R.drawable.answer_btn_before));
            binding.anserBtnBefor.setTextColor(getResources().getColor(R.color.dodgerblue));
        }else{
            binding.anserBtnBefor.setBackground(getResources().getDrawable(R.drawable.answer_btn_next));
            binding.anserBtnBefor.setClickable(true);
            binding.anserBtnBefor.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void btnafterstyle(){

        if(binding.anserCardVp.getCurrentItem() == mViewModel.getAlllist().size()-1){
            binding.anserBtnNext.setClickable(false);
            binding.anserBtnNext.setBackground(getResources().getDrawable(R.drawable.answer_btn_before));
            binding.anserBtnNext.setTextColor(getResources().getColor(R.color.dodgerblue));
        }else{
            binding.anserBtnNext.setBackground(getResources().getDrawable(R.drawable.answer_btn_next));
            binding.anserBtnNext.setClickable(true);
            binding.anserBtnNext.setTextColor(getResources().getColor(R.color.white));
        }
    }




}