package com.sakura.ydhyfinal.homepage;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sakura.ydhyfinal.Activity.SubStoryQuesActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.ActivityStoryWrittingBinding;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

public class StoryWrittingActivity extends AppCompatActivity {

    private ActivityStoryWrittingBinding binding;

    private TabLayoutMediator mediator;

    private int activeSize = 14;
    private int normalSize = 12;


    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = binding.storyQuesTabs.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.storyQuesTabs.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };


    private String[] tabs = {"最新创作","热门创作"};


//    ActivityResultLauncher launcher = registerForActivityResult(new ResultContract(), new ActivityResultCallback<String>() {
//        @Override
//        public void onActivityResult(String result) {
////            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
//        }
//    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_story_writting);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        binding.storyQuesBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.storyQuesBtnsub.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {

                //launcher.launch(true);
                startActivity(new Intent(getApplication(),SubStoryQuesActivity.class));
            }
        });


        //为Viewpager设置适配器 与tab联立
        binding.storyQuesVP.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

        binding.storyQuesVP.setAdapter(new FragmentStateAdapter(this) {
            //定位当前fragment的页面
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return StoryWrittingFragment.newInstance(position);
            }

            //返回指示器长度
            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });


        //设定滑动viewPager时对应tabsbar的相应
        binding.storyQuesVP.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(binding.storyQuesTabs, binding.storyQuesVP, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);

                //这里可以自定义TabView
                TextView tabView = new TextView(StoryWrittingActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{getResources().getColor(R.color.dodgerblue), getResources().getColor(R.color.gray)};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setText(tabs[position]);
                tabView.setTextSize(normalSize);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });

        mediator.attach();





    }



}