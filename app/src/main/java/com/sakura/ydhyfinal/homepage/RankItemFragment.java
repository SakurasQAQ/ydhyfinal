package com.sakura.ydhyfinal.homepage;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sakura.ydhyfinal.R;

public class RankItemFragment extends Fragment {

    private RankItemViewModel mViewModel;
    private static final String POSITION = "position";

    private int mPosition;

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
        return inflater.inflate(R.layout.rank_item_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RankItemViewModel.class);
        // TODO: Use the ViewModel
    }

}