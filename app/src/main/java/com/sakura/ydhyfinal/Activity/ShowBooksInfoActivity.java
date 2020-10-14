package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.ShowBooksInfoViewModel;
import com.sakura.ydhyfinal.databinding.ActivityShowBooksInfoBinding;
import com.sakura.ydhyfinal.utils.ChangeTime;
import com.sakura.ydhyfinal.utils.DelTagUtils;

import java.lang.ref.WeakReference;

public class ShowBooksInfoActivity extends AppCompatActivity {

    private ActivityShowBooksInfoBinding binding;

    private ShowBooksInfoViewModel mViewModel;

    private SkeletonScreen skeletonScreen;
    MyHandler myHandler = new MyHandler(this);


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


        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_books_info);
        View rootview =findViewById(R.id.booksdetail_mianblock);
        mViewModel = new ViewModelProvider(this).get(ShowBooksInfoViewModel.class);

        //获取booksid
        String booksid = getIntent().getStringExtra("booksid");
        mViewModel.getOnlineBooksinfo(booksid);

        //骨骼屏预显示
        skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.skeleton_booksinfo)
                .duration(1000)
                .angle(0)
                .show();



        addobverse();


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

    }

    private void addobverse(){

        mViewModel.getBooksget().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
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



}
