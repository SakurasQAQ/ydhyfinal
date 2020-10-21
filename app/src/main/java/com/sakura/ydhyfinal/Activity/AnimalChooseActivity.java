package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.AnimalChooseViewModel;
import com.sakura.ydhyfinal.databinding.ActivityAnimalChooseBinding;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.lang.ref.WeakReference;

public class AnimalChooseActivity extends AppCompatActivity {

    private SkeletonScreen skeletonScreen;
    MyHandler myHandler = new MyHandler(this);
    private ActivityAnimalChooseBinding binding;

    private AnimalChooseViewModel mViewModel;

    private String userid;

    private int currNum = 0;

    private int kindsNum = 0;


    private String[] kinds = {"ocean_animal_type_haiyangyulei","ocean_animal_type_buru","ocean_animal_type_jipi","ocean_animal_type_haiyangyulei","ocean_animal_type_paxing","ocean_animal_type_qiangchang","ocean_animal_type_jiezhi","ocean_animal_type_ruanti"};


    private OnMultiClickListener clickchange = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.anim_btn_next:
                    currNum = currNum+1;

                    if(currNum>mViewModel.getImagelist().size()-1){
                        currNum = 0;
                    }

                    Glide.with(AnimalChooseActivity.this)
                            .load(mViewModel.getImagelist().get(currNum).toString())
                            .into(binding.animalImg);
                    break;

                case R.id.anim_btn_before:
                    currNum = currNum-1;
                    if (currNum<0){
                        currNum = mViewModel.getImagelist().size()-1;
                    }
                    Glide.with(AnimalChooseActivity.this)
                            .load(mViewModel.getImagelist().get(currNum).toString())
                            .into(binding.animalImg);
                    break;
            }
        }
    };



    public static class MyHandler extends android.os.Handler {
        private final WeakReference<AnimalChooseActivity> activityWeakReference;

        MyHandler(AnimalChooseActivity activity){
            this.activityWeakReference = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().skeletonScreen.hide();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String booksid = getIntent().getStringExtra("booksid");
        userid = getIntent().getStringExtra("userid");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_animal_choose);
        View rootview =findViewById(R.id.animal_root);

        mViewModel = new ViewModelProvider(this).get(AnimalChooseViewModel.class);



        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        //骨骼屏预显示
        skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.skeleton_booksinfo)
                .duration(1000)
                .angle(0)
                .show();



        //请求网络数据判断
        if(binding.rbtnAll.isChecked()){
            kindsNum = 0;
        }
        addobserves();
        //数据缓存判断

        loadingData(kindsNum);



        listenerDoubleGroup();

        addlistener();







    }

    //页面点击事件
    private void addlistener(){

        binding.animToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.animBtnNext.setOnClickListener(clickchange);
        binding.animBtnBefore.setOnClickListener(clickchange);

    }

    //页面添加观察者
    private void addobserves(){



        mViewModel.getTotalpage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                //页数大于一页时
                if(integer > 1){
                    String cagesjude = CacheUtils.getCache(AnimalChooseActivity.this,mViewModel.CURRENTURL+kinds[kindsNum]+"2");

                    if(!TextUtils.isEmpty(cagesjude)){
                        String[] cage = new String[mViewModel.getTotalpage().getValue()-1];

                        for (int i = 2;i<=mViewModel.getTotalpage().getValue();i++){
                            cage[i-2] = CacheUtils.getCache(AnimalChooseActivity.this,mViewModel.CURRENTURL+kinds[kindsNum]+i);

                            mViewModel.processMoreData(cage[i-2],i);
                        }
                    }else{
                        mViewModel.getMoreOnlineAnimals(userid,kinds[kindsNum]);
                    }
                }
            }
        });

        mViewModel.getIsfinshload().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Glide.with(AnimalChooseActivity.this)
                            .load(mViewModel.getImagelist().get(0).toString())
                            .into(binding.animalImg);

                    myHandler.sendEmptyMessage(1);
                }
            }
        });

    }




    //RadioGroup 实现多行单选方法
    private void listenerDoubleGroup(){
        binding.groupBtnTop.setOnCheckedChangeListener(new ClickTopListener());
        binding.groupBtnTopBottom.setOnCheckedChangeListener(new ClickBottomLintener());


    }

    private class ClickTopListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rbtn_all:
                    if(binding.rbtnAll.isChecked()){
                        binding.groupBtnTopBottom.clearCheck();
                    }

                    break;
                case R.id.rbtn_buru:
                    if(binding.rbtnBuru.isChecked()){
                        binding.groupBtnTopBottom.clearCheck();
                    }
                    mViewModel.getImagelist().clear();
                    loadingData(1);
                    break;
                case R.id.rbtn_ruanti:
                    if(binding.rbtnRuanti.isChecked())
                        binding.groupBtnTopBottom.clearCheck();
                    mViewModel.getImagelist().clear();
                    loadingData(7);
                    break;
                case R.id.rbtn_jiezhi:
                    if(binding.rbtnJiezhi.isChecked())
                        binding.groupBtnTopBottom.clearCheck();
                    mViewModel.getImagelist().clear();
                    loadingData(6);
                    break;
            }
        }
    }

    private class ClickBottomLintener implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rbtn_jipi:
                    if(binding.rbtnJipi.isChecked())
                        binding.groupBtnTop.clearCheck();
                    mViewModel.getImagelist().clear();
                    loadingData(2);
                    break;
                case R.id.rbtn_haiyang:
                    if(binding.rbtnHaiyang.isChecked())
                        binding.groupBtnTop.clearCheck();
                    mViewModel.getImagelist().clear();
                    loadingData(3);
                    break;
                case R.id.rbtn_pa:
                    if(binding.rbtnPa.isChecked())
                        binding.groupBtnTop.clearCheck();
                    mViewModel.getImagelist().clear();
                    loadingData(4);
                    break;
                case R.id.rbtn_qiangchang:
                    if(binding.rbtnQiangchang.isChecked()){
                        binding.groupBtnTop.clearCheck();
                    }
                    mViewModel.getImagelist().clear();
                    loadingData(5);
                    break;
            }
        }
    }

    private void loadingData(int nums){

        String cacheone = CacheUtils.getCache(this,mViewModel.CURRENTURL+kinds[nums]+"1");

        if(!TextUtils.isEmpty(cacheone)){
            mViewModel.processData(cacheone);
        }else{
            mViewModel.getOnlineAnimals(userid,kinds[nums]);
        }

    }



}