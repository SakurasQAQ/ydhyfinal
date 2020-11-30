package com.sakura.ydhyfinal.homepage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.RankAdapter;
import com.sakura.ydhyfinal.bean.RankBean;

public class RankItemFragment extends Fragment {

    private RankItemViewModel mViewModel;
    private static final String POSITION = "position";

    private RecyclerView recyclerView;
    private int mPosition;

    private RankAdapter adapter;

    private String[] actrlUrl = {"mobileUser/RankScore","mobileUser/RankBook"};

    public static RankItemFragment newInstance(int position) {
        RankItemFragment fragment = new RankItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION,position);
        fragment.setArguments(bundle);
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
        View view = inflater.inflate(R.layout.rank_item_fragment,container,false);

        recyclerView = view.findViewById(R.id.rank_recyview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RankItemViewModel.class);

        mViewModel.loadRankdara(actrlUrl[mPosition]);

        adapter = new RankAdapter(getContext(),mViewModel.getRanklist());


        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_class)
                .show();

        mViewModel.getLoadfinish().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    recyclerView.setAdapter(adapter);
                    //skeletonScreen.hide();
                    //recyclerView.setAdapter(classAdapter);

                    skeletonScreen.hide();
                }
            }
        });



        // TODO: Use the ViewModel
    }

}