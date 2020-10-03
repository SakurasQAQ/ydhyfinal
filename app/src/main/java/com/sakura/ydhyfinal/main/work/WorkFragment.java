package com.sakura.ydhyfinal.main.work;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.WorkFragmentBinding;
import com.sakura.ydhyfinal.main.task.TaskItemFragment;

public class WorkFragment extends Fragment {

    private WorkViewModel mViewModel;



    private WorkFragmentBinding binding;

    private TabLayoutMediator mediator;

    private int activeSize = 14;
    private int normalSize = 12;

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //设置选中时tab的大小
            int tabCount = binding.workTablayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.workTablayout.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                } else {
                    tabView.setTextSize(normalSize);
                }
            }
        }
    };






    public static WorkFragment newInstance() {
        return new WorkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = WorkFragmentBinding.inflate(inflater);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), getResources().getColor(R.color.white));


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WorkViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化当前页面
        initViewPager();
    }


    private void  initViewPager() {

        //设置导航栏数量及标题
        final String[] tabs = new String[]{"优美诗歌","绘本","自然","童话故事","神话传奇","文史","数学","小说散文","世界名著","名人传记"};

        //为Viewpager设置适配器 与tab联立
        binding.workVP.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.workVP.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return WorkItemFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        //设立连接以及指示器
        mediator = new TabLayoutMediator(binding.workTablayout, binding.workVP, true, (tab, position) -> {
            TextView tabView = new TextView(getContext());
            tabView.setText(tabs[position]);

            //选中时颜色

            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{};
            int[] colors = new int[]{getResources().getColor(R.color.dodgerblue), getResources().getColor(R.color.gray)};
            ColorStateList stateList = new ColorStateList(states, colors);
            tabView.setTextColor(stateList);





            tab.setCustomView(tabView);
        });
        mediator.attach();

    }

}
