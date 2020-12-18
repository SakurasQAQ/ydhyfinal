package com.sakura.ydhyfinal.Fragment;

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

public class BooksBlockDiscussFragment extends Fragment {

    private BooksBlockDiscussViewModel mViewModel;
    private static final String POSITION = "position";

    public static BooksBlockDiscussFragment newInstance(int position) {
        BooksBlockDiscussFragment fragment = new BooksBlockDiscussFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.books_block_discuss_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BooksBlockDiscussViewModel.class);
        // TODO: Use the ViewModel
    }

}