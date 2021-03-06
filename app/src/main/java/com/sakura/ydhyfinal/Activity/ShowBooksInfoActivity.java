package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.ShowBooksInfoViewModel;
import com.sakura.ydhyfinal.adapter.BooksqesAdapter;
import com.sakura.ydhyfinal.databinding.ActivityShowBooksInfoBinding;
import com.sakura.ydhyfinal.dialogView.AnimalsDialog;
import com.sakura.ydhyfinal.utils.ChangeTime;
import com.sakura.ydhyfinal.utils.DelTagUtils;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.lang.ref.WeakReference;

public class ShowBooksInfoActivity extends AppCompatActivity {

    private ActivityShowBooksInfoBinding binding;

    private ShowBooksInfoViewModel mViewModel;

    private SkeletonScreen skeletonScreen;
    private String booksid;
    private String userid;

    MyHandler myHandler = new MyHandler(this);

    private BooksqesAdapter booksqesAdapter;

    private OnMultiClickListener clickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {

            switch (v.getId()){
                case R.id.booksdetail_chooseanl:
                    Log.d("click", "onMultiClick:111111111 ");
                    orderbooks();
                    break;

                case R.id.booksdetail_btn_answer:
                    Intent intent = new Intent();
                    intent.putExtra("bookName",mViewModel.getGetBooksinfo().getTitle());
                    intent.putExtra("bookId",booksid);
                    intent.setClass(getApplication(),AnswerQuesActivity.class);
                    startActivity(intent);

            }
        }
    };




    public static class MyHandler extends android.os.Handler {
        private final WeakReference<ShowBooksInfoActivity> activityWeakReference;

        MyHandler(ShowBooksInfoActivity activity) {
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
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));


        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_books_info);
        View rootview =findViewById(R.id.booksdetail_mianblock);

        mViewModel = new ViewModelProvider(this).get(ShowBooksInfoViewModel.class);

        //获取booksid
        booksid = getIntent().getStringExtra("booksid");
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("userId","");


        //骨骼屏预显示
        skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.skeleton_booksinfo)
                .duration(1000)
                .angle(0)
                .show();






        addlistener();



    }

    private void reflashview(){


        binding.booksdetailTitle.setText(mViewModel.getGetBooksinfo().getTitle());

        binding.booksdetailReadNum.setText(String.valueOf(mViewModel.getGetBooksinfo().getReadNum())+"人读过");

        binding.booksdetailIsreading.setText(String.valueOf(mViewModel.getGetBooksinfo().getReadingNum())+"人正在阅读");

        binding.booksdetailAuthor.setText("作者："+mViewModel.getGetBooksinfo().getAuthor());

        binding.booksdetailPublish.setText("出版社："+mViewModel.getGetBooksinfo().getPress());

        String pubtime = ChangeTime.formatdate(String.valueOf(mViewModel.getGetBooksinfo().getPublishDate()));

        binding.booksdetailPublishtime.setText("出版时间："+ pubtime);

        String wen = DelTagUtils.getTextFromHtml(mViewModel.getGetBooksinfo().getIntroduction());
        String review = DelTagUtils.getTextFromHtml(mViewModel.getGetBooksinfo().getReview());

        binding.booksdetailBookswen.setText("       "+wen);

        binding.booksdetailBookreview.setText("        "+review);

        Glide.with(this).load(mViewModel.getGetBooksinfo().getCoverImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(binding.booksdetailImgPic);


        if(mViewModel.getGetBooksinfo().getCourses().size()==0){


            binding.booksdetailsBooksclassblockNotag.setText("暂无微课视频！");
            binding.booksdetailsBooksclassblockNotag.setVisibility(View.VISIBLE);
            binding.booksdetailsBooksclassblockKeblok.setVisibility(View.GONE);

        }else{
            binding.booksdetailsBooksclassblockNotag.setVisibility(View.GONE);
            binding.booksdetailsBooksclassblockKeblok.setVisibility(View.VISIBLE);

            binding.booksdetailsBooksclassblockKeblokTit.setText("《"+mViewModel.getGetBooksinfo().getCourses().get(0).getTitle()+"》");
            binding.booksdetailsBooksclassblockKeblokAut.setText("   ————"+mViewModel.getGetBooksinfo().getCourses().get(0).getAuthor());
        }


        if(mViewModel.getBooksorder().getResult().equals("notsub")){
            binding.booksdetailChooseanl.setVisibility(View.VISIBLE);
            binding.booksdetailIschooseblock.setVisibility(View.GONE);
            binding.booksdetailBtnAnswer.setVisibility(View.GONE);
            binding.booksdetailsRecyDati.setVisibility(View.GONE);
            binding.booksdetailsBooksqesblockNotag.setVisibility(View.VISIBLE);
            binding.booksdetailsBooksqesblockNotag.setText("未订阅该书籍，无法查看！");
            binding.booksdetailsNotsubTxt.setVisibility(View.VISIBLE);
        }else if(mViewModel.getBooksorder().getResult().equals("notfinish")){
            binding.booksdetailsNotsubTxt.setVisibility(View.GONE);
            binding.booksdetailChooseanl.setVisibility(View.GONE);
            binding.booksdetailIschooseblock.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mViewModel.getBooksorder().getdata().getUrl())
                    .into(binding.booksdetailAninmalimg);
            binding.booksdetailAninmalname.setText("目标生物：\n"+mViewModel.getBooksorder().getdata().getAnimalName());
            binding.booksdetailBtnAnswer.setVisibility(View.VISIBLE);

            if(mViewModel.getAnswerList().size()!=0){
                binding.booksdetailsBooksqesblockNotag.setVisibility(View.GONE);

                binding.booksdetailsRecyDati.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                binding.booksdetailsRecyDati.setLayoutManager(linearLayoutManager);
                booksqesAdapter = new BooksqesAdapter(this,mViewModel.getAnswerList());
                binding.booksdetailsRecyDati.setAdapter(booksqesAdapter);
            }else{
                binding.booksdetailsRecyDati.setVisibility(View.GONE);
                binding.booksdetailsBooksqesblockNotag.setVisibility(View.VISIBLE);
                binding.booksdetailsBooksqesblockNotag.setText("暂无答题记录！");
            }
        }else if(mViewModel.getBooksorder().getResult().equals("finish")){
            binding.booksdetailsNotsubTxt.setVisibility(View.GONE);
            binding.booksdetailChooseanl.setVisibility(View.GONE);
            binding.booksdetailIschooseblock.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mViewModel.getBooksorder().getdata().getUrl())
                    .into(binding.booksdetailAninmalimg);
            binding.booksdetailAninmalname.setText(mViewModel.getBooksorder().getdata().getAnimalName());
            binding.booksdetailBtnAnswer.setVisibility(View.VISIBLE);

            binding.booksdetailBtnAnswer.setText("已完成");
            binding.booksdetailBtnAnswer.setClickable(false);

            if(mViewModel.getAnswerList().size()!=0){
                binding.booksdetailsBooksqesblockNotag.setVisibility(View.GONE);
                binding.booksdetailsRecyDati.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                binding.booksdetailsRecyDati.setLayoutManager(linearLayoutManager);
                booksqesAdapter = new BooksqesAdapter(this,mViewModel.getAnswerList());
                binding.booksdetailsRecyDati.setAdapter(booksqesAdapter);
            }else{
                binding.booksdetailsRecyDati.setVisibility(View.GONE);
                binding.booksdetailsBooksqesblockNotag.setVisibility(View.VISIBLE);
                binding.booksdetailsBooksqesblockNotag.setText("暂无答题记录！");
            }
        }






    }

    private void addobverse(){

        mViewModel.getBooksget().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 2){
                    reflashview();
                    myHandler.sendEmptyMessage(1);
                }
            }
        });

    }




    private void addlistener(){
        binding.toolbarbooksdetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.booksdetailChooseanl.setOnClickListener(clickListener);

        binding.booksdetailBtnAnswer.setOnClickListener(clickListener);



        binding.radiogroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int fontB = Typeface.BOLD;
                int font = Typeface.NORMAL;
                if(checkedId == R.id.booksdetails_radioBtn1){

                    binding.booksdetailsRadioBtn1.setTypeface(Typeface.defaultFromStyle(fontB));
                    binding.booksdetailsRadioBtn2.setTypeface(Typeface.defaultFromStyle(font));
                    binding.booksdetailsRadioBtn3.setTypeface(Typeface.defaultFromStyle(font));

                    binding.booksdetailsBooksinfoblock.setVisibility(View.VISIBLE);
                    binding.booksdetailsBooksqesblock.setVisibility(View.GONE);
                    binding.booksdetailsBooksclassblock.setVisibility(View.GONE);
                }
                if(checkedId == R.id.booksdetails_radioBtn2){

                    binding.booksdetailsRadioBtn1.setTypeface(Typeface.defaultFromStyle(font));
                    binding.booksdetailsRadioBtn2.setTypeface(Typeface.defaultFromStyle(fontB));
                    binding.booksdetailsRadioBtn3.setTypeface(Typeface.defaultFromStyle(font));

                    binding.booksdetailsBooksinfoblock.setVisibility(View.GONE);
                    binding.booksdetailsBooksqesblock.setVisibility(View.VISIBLE);
                    binding.booksdetailsBooksclassblock.setVisibility(View.GONE);

                }
                if(checkedId == R.id.booksdetails_radioBtn3){

                    binding.booksdetailsRadioBtn1.setTypeface(Typeface.defaultFromStyle(font));
                    binding.booksdetailsRadioBtn2.setTypeface(Typeface.defaultFromStyle(font));
                    binding.booksdetailsRadioBtn3.setTypeface(Typeface.defaultFromStyle(fontB));
                    binding.booksdetailsBooksinfoblock.setVisibility(View.GONE);
                    binding.booksdetailsBooksqesblock.setVisibility(View.GONE);
                    binding.booksdetailsBooksclassblock.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void orderbooks(){
        AnimalsDialog animalsDialog = new AnimalsDialog(this,R.style.Dialog_Msg);
        animalsDialog.setMyOnclickListener(new AnimalsDialog.MyOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent = new Intent();
                intent.putExtra("booksid",booksid);
                intent.putExtra("userid",userid);
                intent.putExtra("booksname",mViewModel.getGetBooksinfo().getTitle());

                intent.setClass(getApplication(),AnimalChooseActivity.class);
                startActivity(intent);
                animalsDialog.dismiss();
            }
        });

        animalsDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewModel = new ViewModelProvider(this).get(ShowBooksInfoViewModel.class);
        mViewModel.judgeorder(booksid);

        mViewModel.getOnlineBooksinfo(booksid);

        mViewModel.getBooksget().setValue(0);

        addobverse();
//        reflashview();
    }
}
