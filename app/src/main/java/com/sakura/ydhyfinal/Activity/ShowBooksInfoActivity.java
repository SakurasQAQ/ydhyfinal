package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.ActivityShowBooksInfoBinding;

public class ShowBooksInfoActivity extends AppCompatActivity {

    private ActivityShowBooksInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_books_info);

        //binding = DataBindingUtil.setContentView(this,R.layout.activity_show_books_info);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_books_info);

        addlistener();

        Glide.with(this)
                .load("http://ro.bnuz.edu.cn/book/category_shige/43476dfb-2ea6-4901-aa6e-26f6e776288f.png")
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(binding.booksdetailImg);

    }

    private void addlistener(){
        binding.toolbarbooksdetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



}
