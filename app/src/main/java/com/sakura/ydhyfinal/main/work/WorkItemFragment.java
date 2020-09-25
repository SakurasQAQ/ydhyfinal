package com.sakura.ydhyfinal.main.work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.TestAdapter;
import com.sakura.ydhyfinal.adapter.WorkItemAdapter;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.databinding.FragmentTaskItemBinding;
import com.sakura.ydhyfinal.databinding.FragmentWorkItemBinding;
import com.sakura.ydhyfinal.databinding.WorkFragmentBinding;
import com.sakura.ydhyfinal.utils.EndlessRecyclerOnScrollListener;
import com.sakura.ydhyfinal.utils.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class WorkItemFragment extends Fragment {

    private FragmentWorkItemBinding binding;

    private static final String POSITION = "position";
    private int mPosition;

    private TestAdapter mytestadapter;

    private ArrayList<MyWorks> works = new ArrayList<>();


    private WorkItemViewModel mViewModel;

    private Handler mHandler;

    private int pages = 1;

    private int totalpager,currentpager;

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

        mytestadapter = new TestAdapter(works);

        binding.workswipeRefreshLayout.setColorSchemeResources(R.color.lightskyblue);




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recylcerViewWork.setAdapter(mytestadapter);



        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false);
        //binding.recylcerViewWork.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recylcerViewWork.setLayoutManager(layoutManager);
        binding.workswipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> binding.workswipeRefreshLayout.setRefreshing(false), 500);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WorkItemViewModel.class);

        mViewModel.getonlineData(cages[mPosition],mPosition);

        mViewModel.getItemstatus().observe(getViewLifecycleOwner(),(integer -> {
            if(integer != 0){
                initWorkData();
                mytestadapter.notifyDataSetChanged();
            }
        }));



//        mViewModel.getCurrentpages().observe(getViewLifecycleOwner(),integers -> {
//            Log.d("current:"," "+integers);
//            if(integers == mViewModel.totalspages && mViewModel.totalspages!=0){
//                Toast.makeText(getContext(),"最大",Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.recylcerViewWork.addOnScrollListener(new EndlessRecyclerOnScrollListener(true) {




            @Override
            public void onLoadMore() {
                pages = pages + 1;

                Log.d("total&pages", "onLoadMore: "+mViewModel.getbacklist[mPosition].getTotalPage() +"   "+pages);

                if(mViewModel.getbacklist[mPosition].getTotalPage() >= pages){
                    Thread thread = new Thread(() ->
                            mViewModel.getonlineDatanumbers(pages,mPosition,cages[mPosition]));
                    thread.start();

                    mViewModel.getCurrentpager().observe(getViewLifecycleOwner(),(integer -> {
                        if(integer == pages){
//                            new Handler().postDelayed(() -> {
                                initWorkDataMore();
                                mytestadapter.notifyDataSetChanged();
//                            }, 2000);
                        }
                    }));


                }else{
                    //禁止向下滑动事件
                    binding.recylcerViewWork.addOnScrollListener(new EndlessRecyclerOnScrollListener(false) {
                        @Override
                        public void onLoadMore() {
                        }
                    });
                }


            }
        });



















    }

    private void initWorkData(){

        for(int i=0;i<mViewModel.getbacklist[mPosition].getDataList().size();i++){

            MyWorks work = new MyWorks();
            work.setWorksName(mViewModel.getbacklist[mPosition].getDataList().get(i).getTitle());
            work.setWorksId(mViewModel.getbacklist[mPosition].getDataList().get(i).getId());
            work.setWorksPicUrl(mViewModel.getbacklist[mPosition].getDataList().get(i).getImg());
            work.setPostNum(mViewModel.getbacklist[mPosition].getDataList().get(i).getPostNum());
            work.setThumbNumbers(mViewModel.getbacklist[mPosition].getDataList().get(i).getThumbNumbers());
            works.add(work);

        }

    }

    private void initWorkDataMore(){

        for(int i=0;i<mViewModel.getbacklistmore[mPosition].getDataList().size();i++){

            MyWorks work = new MyWorks();
            work.setWorksName(mViewModel.getbacklistmore[mPosition].getDataList().get(i).getTitle());
            work.setWorksId(mViewModel.getbacklistmore[mPosition].getDataList().get(i).getId());
            work.setWorksPicUrl(mViewModel.getbacklistmore[mPosition].getDataList().get(i).getImg());
            work.setPostNum(mViewModel.getbacklistmore[mPosition].getDataList().get(i).getPostNum());
            work.setThumbNumbers(mViewModel.getbacklistmore[mPosition].getDataList().get(i).getThumbNumbers());
            works.add(work);

        }
    }










}
