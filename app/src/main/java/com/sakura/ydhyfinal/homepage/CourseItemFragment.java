package com.sakura.ydhyfinal.homepage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.CourseClassAdapter;
import com.sakura.ydhyfinal.bean.Coursesclass;

import java.util.ArrayList;

public class CourseItemFragment extends Fragment {

    private CourseItemViewModel mViewModel;
    private static final String POSITION = "position";
    private int mPosition;

    private CourseClassAdapter classAdapter;

    private RecyclerView recyclerView;

    private ArrayList<Coursesclass> mylist = new ArrayList<>();

    private String[] typearr = new String[]{"tinyread_type_book","tinyread_type_func","tinyread_type_video"};


    public static CourseItemFragment newInstance(int position) {

        CourseItemFragment fragment= new CourseItemFragment();
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

        View view = inflater.inflate(R.layout.course_item_fragment, container, false);

        recyclerView = view.findViewById(R.id.class_RecyView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        //classAdapter = new CourseClassAdapter(getContext(),mylist);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CourseItemViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getOnlineCourse(typearr[mPosition]);

        classAdapter = new CourseClassAdapter(getContext(),mViewModel.getCourseList());

        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(classAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_class)
                .show();

        mViewModel.getJudeload().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if(integer == 1){



                    recyclerView.setAdapter(classAdapter);
                    //skeletonScreen.hide();
                    //recyclerView.setAdapter(classAdapter);

                    skeletonScreen.hide();

                }
            }
        });





    }

}