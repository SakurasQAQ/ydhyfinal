package com.sakura.ydhyfinal.main.task;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.TaskFragmentBinding;

public class TaskFragment extends Fragment {

    private TaskViewModel mViewModel;
    private TabLayoutMediator mediator;
    private TaskFragmentBinding binding;


    private int activeSize = 14;
    private int normalSize = 12;

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //设置选中时tab的大小
            int tabCount = binding.taskTab.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = binding.taskTab.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                } else {
                    tabView.setTextSize(normalSize);
                }
            }
        }
    };


    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TaskFragmentBinding.inflate(inflater);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), getResources().getColor(R.color.white));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        initViewPager();
        // TODO: Use the ViewModel
    }


    private void  initViewPager(){

        final String[] tabs = new String[]{"教师任务", "完成任务","逾期任务"};
        binding.taskVP.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.taskVP.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return TaskItemFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        //监听
        binding.taskVP.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(binding.taskTab, binding.taskVP, true, (tab, position) -> {
            TextView tabView = new TextView(getContext());
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

        });
        mediator.attach();

    }
}
