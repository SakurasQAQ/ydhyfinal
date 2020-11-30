package com.sakura.ydhyfinal.dialogView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.Activity.ShowBooksInfoActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;


public class ParentReadingDialog extends Dialog {

    private ImageView btn_close;

    private ImageView bookimg1,bookimg2,bookimg3;

    private TextView tit1,tit2,tit3,cont1,cont2,cont3;

    private CardView cd1,cd2,cd3;

    private Intent intent;



    public ParentReadingDialog(@NonNull Context context) {
        super(context);
    }

    public ParentReadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private OnMultiClickListener clickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()) {

                case R.id.dia_btnclose:
                    dismiss();
                    break;

                case R.id.parentReading_bk1:
                    intent = new Intent();
                    intent.putExtra("booksid","cb8ad662-ac25-11e8-98d0-529269fb1459");
                    intent.setClass(getContext(), ShowBooksInfoActivity.class);
                    getContext().startActivity(intent);
                    dismiss();
                    break;

                case R.id.parentReading_bk2:
                    intent = new Intent();
                    intent.putExtra("booksid","47894a5a-b1bc-11e8-96f8-529269fb1459");
                    intent.setClass(getContext(), ShowBooksInfoActivity.class);
                    getContext().startActivity(intent);
                    dismiss();
                    break;

                case R.id.parentReading_bk3:
                    intent = new Intent();
                    intent.putExtra("booksid","7c9f3a8e-967f-40b2-9687-9f1b7a86193d");
                    intent.setClass(getContext(), ShowBooksInfoActivity.class);
                    getContext().startActivity(intent);
                    dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_parent_reading);
        setCanceledOnTouchOutside(false);

        initView();

        addLinstener();

        initData();

    }

    private void initData(){

        Glide.with(getContext())
                .load("http://ro.bnuz.edu.cn/book/category_parents/73b2e96e-ac2c-11e8-98d0-529269fb1459.jpg")
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(bookimg1);

        tit1.setText("新父母学校");
        cont1.setText("     陪伴孩子，没有人能代替父母。道法自然。自然的教育，就是好的教育。影响学生成绩的主要原因不是学校，而是家庭。孩子的成长有三个关键期，让孩子在关键年龄做关键事情。");


        Glide.with(getContext())
                .load("http://ro.bnuz.edu.cn/book/category_parents/9dc433ba-ac2d-11e8-98d0-529269fb1459.jpg")
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(bookimg2);

        tit2.setText("皇帝内经");
        cont2.setText("     《黄帝内经》是中国人必备一册的中医养生经典，重塑中国人养生保健的国医绝学！读懂、用会第一部生命智慧百科全书。破解生死迷津，掌握健康根本，阴阳调和看四季！汲取智者千年养生精华，百病从此不再生！");


        Glide.with(getContext())
                .load("http://ro.bnuz.edu.cn/book/category_parents/7c9f3a8e-967f-40b2-9687-9f1b7a86193d.jpg")
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(bookimg3);

        tit3.setText("心理学与生活");
        cont3.setText("     作为一本包含着丰富的教育思想和独特教学方法的成熟教材，原书中所有元素——如由600余条词汇及解释组成的“专业术语表”，2000余条“参考文献”，以及近1000条的“人名和主题索引”等等，对于教学、研究和学习都十分宝贵，此中译本完整地翻译和保留了这些资料。");


    }


    private void initView(){
        btn_close = findViewById(R.id.dia_btnclose);

        bookimg1 = findViewById(R.id.parentReading_bookimg1);
        bookimg2 = findViewById(R.id.parentReading_bookimg2);
        bookimg3 = findViewById(R.id.parentReading_bookimg3);

        tit1 = findViewById(R.id.parentReading_booktit1);
        tit2 = findViewById(R.id.parentReading_booktit2);
        tit3 = findViewById(R.id.parentReading_booktit3);

        cont1 = findViewById(R.id.parentReading_bookcontext);
        cont2 = findViewById(R.id.parentReading_bookcontext2);
        cont3 = findViewById(R.id.parentReading_bookcontext3);

        cd1 = findViewById(R.id.parentReading_bk1);
        cd2 = findViewById(R.id.parentReading_bk2);
        cd3 = findViewById(R.id.parentReading_bk3);





    }


    private void addLinstener(){

        btn_close.setOnClickListener(clickListener);

        cd1.setOnClickListener(clickListener);
        cd2.setOnClickListener(clickListener);
        cd3.setOnClickListener(clickListener);


    }


    @Override
    public void show() {
        super.show();


        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= 980;
        layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x=0;
        layoutParams.y=50;
        //getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
