package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sakura.ydhyfinal.Fragment.BooksBlockDiscussFragment;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.BooksBlocksViewModel;
import com.sakura.ydhyfinal.databinding.ActivityBooksBlocksBinding;
import com.sakura.ydhyfinal.main.work.WorkItemFragment;


public class BooksBlocksActivity extends AppCompatActivity {

    private ActivityBooksBlocksBinding binding;

    private BooksBlocksViewModel mViewModel;

    private TabLayoutMediator mediator;


    String[] typeStr = {"全部","短评","读后感","绘画","思维导图","读书笔记","朗读","背诵"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_books_blocks);
        mViewModel = new ViewModelProvider(this).get(BooksBlocksViewModel.class);





        //为Viewpager设置适配器 与tab联立
        binding.bookblockVP.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.bookblockVP.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                return WorkItemFragment.newInstance(position);

                return BooksBlockDiscussFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return typeStr.length;
            }
        });


        //设立连接以及指示器
        mediator = new TabLayoutMediator(binding.bookblockGetuptabs, binding.bookblockVP, true, (tab, position) -> {
            TextView tabView = new TextView(this);
            tabView.setText(typeStr[position]);
            //选中时颜色
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{};
            int[] colors = new int[]{getResources().getColor(R.color.dodgerblue), getResources().getColor(R.color.gray)};
            ColorStateList stateList = new ColorStateList(states, colors);
            tabView.setTextColor(stateList);

            int font = Typeface.BOLD;
            tabView.setTypeface(Typeface.defaultFromStyle(font));
            tab.setCustomView(tabView);



            tab.setCustomView(tabView);
        });
        mediator.attach();




        Glide.with(this).load("http://ro.bnuz.edu.cn/book/category_manhua/4aa3fccd-d816-428b-aa50-1bf9e5bafa32.png")
                .apply(binding.bookblockTopbackimg.setGaussBlur())//这是重点
                .into(binding.bookblockTopbackimg);






    }






}