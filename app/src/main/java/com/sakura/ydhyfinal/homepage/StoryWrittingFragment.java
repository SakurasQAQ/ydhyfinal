package com.sakura.ydhyfinal.homepage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sakura.ydhyfinal.DataSourse.PageDataQuestionFactory;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.QuesAnswerAdapter;
import com.sakura.ydhyfinal.bean.StoryQues;

public class StoryWrittingFragment extends Fragment {

    private StoryWrittingViewModel mViewModel;

    private static final String POSITION = "position";
    private int mPosition;

    private RecyclerView recyclerView;

    private QuesAnswerAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private String[] typeVar = {" ","hot"};

    public static StoryWrittingFragment newInstance(int position) {
        StoryWrittingFragment fragment= new StoryWrittingFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.story_writting_fragment, container, false);

        recyclerView = view.findViewById(R.id.story_ques_recy);

        refreshLayout = view.findViewById(R.id.story_ques_reflash);

        refreshLayout.setColorSchemeColors(getContext().getColor(R.color.dodgerblue));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new QuesAnswerAdapter(new DiffUtil.ItemCallback<StoryQues>() {
            @Override
            public boolean areItemsTheSame(@NonNull StoryQues oldItem, @NonNull StoryQues newItem) {

                return oldItem.getQuestionId() == newItem.getQuestionId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull StoryQues oldItem, @NonNull StoryQues newItem) {
                return oldItem.getQuestionTime() == newItem.getQuestionTime();
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StoryWrittingViewModel.class);
        mViewModel.getPagecage().setValue(typeVar[mPosition]);
        mViewModel.getPos().setValue(mPosition);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.submitList(null);
                mViewModel.getmLivedata().observe(getViewLifecycleOwner(), new Observer<PagedList<StoryQues>>() {
                    @Override
                    public void onChanged(PagedList<StoryQues> storyQues) {
                        refreshLayout.setRefreshing(false);
                        mViewModel.mPage = 1;
                        adapter.submitList(storyQues);

                    }
                });


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 500);


            }
        });




        mViewModel.getmLivedata().observe(getViewLifecycleOwner(), new Observer<PagedList<StoryQues>>() {
            @Override
            public void onChanged(PagedList<StoryQues> storyQues) {
                if(storyQues != null){
                   adapter.submitList(storyQues);
                }
            }
        });


        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(9)
                .load(R.layout.item_skeleton_rank)
                .show();

        mViewModel.getJudes().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    skeletonScreen.hide();

                }
            }
        });


        // TODO: Use the ViewModel
    }

}