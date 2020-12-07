package com.sakura.ydhyfinal.main.work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.WorkItemAdapter;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.databinding.FragmentWorkItemBinding;


public class WorkItemFragment extends Fragment {

    private FragmentWorkItemBinding binding;

    private static final String POSITION = "position";
    private int mPosition;

    private boolean flags = false;

    //private Booklistdetailadapter myadapter;

    private WorkItemAdapter workItemAdapter;

    //private ArrayList<MyWorks> works = new ArrayList<>();


    private WorkItemViewModel mViewModel;


    final String[] cages = new String[]{"category_shige","category_kexue","category_manhua","category_tonghua","category_shenhua","category_lishi","category_shuxue","category_xiaoshuo","category_mingzhu","category_mingren"};




    public WorkItemFragment() {
        // Required empty public constructor
    }


    // 重构接口
    public static WorkItemFragment newInstance(int position) {
        WorkItemFragment fragment = new WorkItemFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkItemBinding.inflate(inflater);
        binding.setLifecycleOwner(getActivity());


//        binding.workswipeRefreshLayout.setColorSchemeResources(R.color.dodgerblue);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false){

            @Override
            public boolean canScrollVertically() {
                return flags;
            }

        };

        binding.recylcerViewWork.setLayoutManager(layoutManager);

        workItemAdapter = new WorkItemAdapter(new DiffUtil.ItemCallback<MyWorks>() {
            @Override
            public boolean areItemsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksId() == newItem.getWorksId();

            }


            @Override
            public boolean areContentsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksName().equals(newItem.getWorksName());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //binding.recylcerViewWork.setAdapter(mytestadapter);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WorkItemViewModel.class);
        mViewModel.getPagecage().setValue(cages[mPosition]);
        mViewModel.getPos().setValue(mPosition);
        mViewModel.getmLivedata().observe(getViewLifecycleOwner(), new Observer<PagedList<MyWorks>>() {
            @Override
            public void onChanged(PagedList<MyWorks> myWorks) {
                if (myWorks != null) {

                    workItemAdapter.submitList(myWorks);

                }

            }
        });

        final SkeletonScreen skeletonScreen = Skeleton.bind(binding.recylcerViewWork)
                .adapter(workItemAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(9)
                .load(R.layout.item_skeleton_works)
                .show();

        mViewModel.getJudes().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    skeletonScreen.hide();
                    flags = true;
                }
            }
        });
//        binding.workswipeRefreshLayout.setOnRefreshListener(() -> {
//
////            mViewModel.getPagecage().setValue(cages[mPosition]);
////            mViewModel.getPos().setValue(mPosition);
//            workItemAdapter.notifyDataSetChanged();
//            Toast.makeText(getContext(),"加载中",Toast.LENGTH_SHORT).show();
//
////            mViewModel.getmLivedata().observe(getViewLifecycleOwner(), new Observer<PagedList<MyWorks>>() {
////                @Override
////                public void onChanged(PagedList<MyWorks> myWorks) {
////                    if (myWorks != null) {
////
////                        workItemAdapter.submitList(myWorks);
////
////                    }
////
////                }
////            });
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//
//
//                    binding.workswipeRefreshLayout.setRefreshing(false);
//
//
//                }
//            }, 500);
//
//
//            //binding.recylcerViewWork.setAdapter(workItemAdapter);
//
//
//        });


    }




}
