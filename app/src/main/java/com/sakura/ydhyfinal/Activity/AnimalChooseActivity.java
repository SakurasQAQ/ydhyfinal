package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.AnimalChooseViewModel;
import com.sakura.ydhyfinal.adapter.AnimalImgsAdapter;
import com.sakura.ydhyfinal.bean.Animals;
import com.sakura.ydhyfinal.databinding.ActivityAnimalChooseBinding;
import com.sakura.ydhyfinal.dialogView.AnimalImgshowDialog;
import com.sakura.ydhyfinal.dialogView.AnimalsDialog;
import com.sakura.ydhyfinal.dialogView.AnimalsDialogMakesure;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.MyRadioGroup;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AnimalChooseActivity extends AppCompatActivity {

    private SkeletonScreen skeletonScreen;
    MyHandler myHandler = new MyHandler(this);
    private ActivityAnimalChooseBinding binding;

    private AnimalChooseViewModel mViewModel;

    private String userid;
    private String booksid;



    private int currNum = 0;

    private int kindsNum = 0;

    private Handler handler = new Handler();

    private ArrayList<Animals> allList = new ArrayList<>();


    private String[] kinds = {"ocean_animal_type_haiyangyulei","ocean_animal_type_buru","ocean_animal_type_jipi","ocean_animal_type_haiyangyulei","ocean_animal_type_paxing","ocean_animal_type_qiangchang","ocean_animal_type_jiezhi","ocean_animal_type_ruanti"};


    private OnMultiClickListener clickchange = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.anim_btn_next:
                    currNum = currNum+1;

                    if(currNum>mViewModel.getAnimalsinfoList().size()-1){
                        currNum = 0;
                    }
                    showpageinfo(currNum);

//                    Glide.with(AnimalChooseActivity.this)
//                            .load(mViewModel.getAnimalsinfoList().get(currNum).getImg())
//                            .into(binding.animalImg);
                    break;

                case R.id.anim_btn_before:
                    currNum = currNum-1;
                    if (currNum<0){
                        currNum = mViewModel.getAnimalsinfoList().size()-1;
                    }
                    showpageinfo(currNum);
                    Log.d("shownum", "onMultiClick: "+currNum+" ======= "+mViewModel.getAnimalsinfoList().size());
//                    Glide.with(AnimalChooseActivity.this)
//                            .load(mViewModel.getAnimalsinfoList().get(currNum).getImg())
//                            .into(binding.animalImg);
                    break;

                case R.id.cho_btn_animschoose:
                    showimgdialog();
                    break;

                case R.id.cho_btn_makesure:
                    showmaksuredialog(currNum,userid,booksid);
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



        booksid = getIntent().getStringExtra("booksid");

        String booksname = getIntent().getStringExtra("booksname");

        userid = getIntent().getStringExtra("userid");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_animal_choose);
        View rootview =findViewById(R.id.animal_root);
        binding.choBookTit.setText("《"+booksname+"》");
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

        loadingData(kindsNum,false);



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
        binding.choBtnAnimschoose.setOnClickListener(clickchange);

        binding.choBtnMakesure.setOnClickListener(clickchange);




    }

    //页面添加观察者
    private void addobserves(){



        mViewModel.getIsfinshload().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){


                    showpageinfo(0);
//                    Glide.with(AnimalChooseActivity.this)
//                            .load(mViewModel.getAnimalsinfoList().get(0).getImg())
//                            .into(binding.animalImg);
                    allList = mViewModel.getAnimalsinfoList();
                    Log.d("showmylist++++++", "onChanged: "+allList.size()+allList.get(0).getName());

                    myHandler.sendEmptyMessage(1);
                }
            }
        });

    }




    //RadioGroup 实现多行单选方法
    private void listenerDoubleGroup(){
//        binding.groupBtnTop.setOnCheckedChangeListener(new ClickTopListener());
//        binding.groupBtnTopBottom.setOnCheckedChangeListener(new ClickBottomLintener());

        binding.radiosgroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId){

                case R.id.rbtn_all:
                    kindsNum = 0;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(0,false);
                    loadexistData();

                    break;
                case R.id.rbtn_buru:
                    kindsNum = 1;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(1,true);

                    break;
                case R.id.rbtn_ruanti:
                    kindsNum = 7;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(7,true);
                    break;
                case R.id.rbtn_jiezhi:
                    kindsNum = 6;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(6,true);
                    break;

                case R.id.rbtn_jipi:
                    kindsNum = 2;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(2,true);
                    break;
                case R.id.rbtn_haiyang:
                    kindsNum = 3;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(3,true);
                    break;
                case R.id.rbtn_pa:
                    kindsNum = 4;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(4,true);
                    break;
                case R.id.rbtn_qiangchang:
                    kindsNum = 5;
                    currNum = 0;
                    mViewModel.getAnimalsinfoList().clear();
                    loadingData(5,true);
                    break;

                }
            }
        });


    }


    private void loadingData(int nums,boolean jude){
        mViewModel.getOnlineAnimals(userid,kinds[nums],jude);
    }

    private void loadexistData(){}


    private void showpageinfo(int currentnum){
        Glide.with(AnimalChooseActivity.this)
                .load(mViewModel.getAnimalsinfoList().get(currentnum).getImg())
                .into(binding.animalImg);
        binding.choTxtName.setText(mViewModel.getAnimalsinfoList().get(currentnum).getName());
        binding.choTxtLevel.setText("获取等级："+mViewModel.getAnimalsinfoList().get(currentnum).getLevel());
        binding.choTxtNums.setText("已经拥有："+mViewModel.getAnimalsinfoList().get(currentnum).getHasNum());

        binding.choIntroduce.setText("      "+mViewModel.getAnimalsinfoList().get(currentnum).getIntroduction());

        binding.animalTxtnum1.setText(String.valueOf(mViewModel.getAnimalsinfoList().size()));
        binding.animalTxtnum2.setText(String.valueOf(currentnum+1));


    }


    private void showimgdialog(){

        AnimalImgshowDialog animalImgshowDialog = new AnimalImgshowDialog(this,R.style.Dialog_Msg,userid,allList);

        animalImgshowDialog.SetDialogContent(new AnimalImgshowDialog.DialogContent() {
            @Override
            public void ImgClicks(int pos) {
                Log.d("外部显示当前数", "ImgClicks: "+pos);
                animalImgshowDialog.dismiss();
                showpageinfo(pos);
                currNum = pos;
            }

        });

        animalImgshowDialog.show();

    }

    private void showmaksuredialog(int currNum,String uid,String bookid){
        AnimalsDialogMakesure dialogMakesure = new AnimalsDialogMakesure(this,R.style.Dialog_Msg);
        dialogMakesure.setMyOnclickListener(new AnimalsDialog.MyOnclickListener() {
            @Override
            public void onYesClick() {

                String createdid = allList.get(currNum).getId();

                mViewModel.OrderBook(uid,bookid,createdid);

                dialogMakesure.dismiss();

                Toast.makeText(getApplication(),"订阅成功",Toast.LENGTH_SHORT).show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);


                Log.d("当前信息：", "userId"+uid+"::::::::::::生物Id"+mViewModel.getAnimalsinfoList().get(currNum).getId()+"：：：：：：：：：bookId"+bookid);
            }
        });

        dialogMakesure.show();
    }






}