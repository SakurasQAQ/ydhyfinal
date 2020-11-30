package com.sakura.ydhyfinal.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.RanksViewModel;
import com.sakura.ydhyfinal.databinding.ActivityRanksBinding;


public class RanksActivity extends AppCompatActivity {

    private ActivityRanksBinding binding;
    private RanksViewModel mViewModel;
    private TabLayoutMediator mediator;
    private int activeSize = 16;
    private int normalSize = 14;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_ranks);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));


        binding.toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViewPager();
    }

    private void initViewPager(){
        final String tabs[] = {"积分榜","书香榜"};
        binding.rankVp.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.rankVp.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return RankItemFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        binding.rankVp.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(binding.rankTab, binding.rankVp, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView tabView = new TextView(getApplication());
                tabView.setText(tabs[position]);

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

            }
        });
        mediator.attach();
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = binding.rankTab.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.rankTab.getTabAt(i);
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

}