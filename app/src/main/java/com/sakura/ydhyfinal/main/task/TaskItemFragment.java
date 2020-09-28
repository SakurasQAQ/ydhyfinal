package com.sakura.ydhyfinal.main.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.MyTaskAdapter;
import com.sakura.ydhyfinal.bean.MyTask;
import com.sakura.ydhyfinal.databinding.FragmentTaskItemBinding;
import com.sakura.ydhyfinal.utils.ChangeTime;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class TaskItemFragment extends Fragment {

    private FragmentTaskItemBinding binding;

    private static final String POSITION = "position";
    private int mPosition;


    private MyTaskAdapter taskAdapter;

    private ArrayList<MyTask> tasks = new ArrayList<>();

    private TaskItemViewModel mViewModel;

    private Handler mHandler;

    final static String[] taskcage = new String[]{"&type=pending", "", "&type=overdue"};



    public static TaskItemFragment newInstance(int position) {
        TaskItemFragment fragment = new TaskItemFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentTaskItemBinding.inflate(inflater);

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.lightskyblue);




        //inittaskData();
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskAdapter = new MyTaskAdapter(tasks);

        binding.recylcerViewTask.setAdapter(taskAdapter);
        binding.recylcerViewTask.setLayoutManager(new LinearLayoutManager(getContext()));
//        if (mPosition == 0) {
//            binding.recylcerViewTask.setAdapter(taskAdapter);
//        } else if (mPosition == 1) {
//            //binding.recylcerViewTask.setAdapter((RecyclerView.Adapter) adapter);
//        }else if ((mPosition == 2)) {
//            binding.recylcerViewTask.setAdapter(taskAdapter);
//
//
//        }

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> binding.swipeRefreshLayout.setRefreshing(false), 500);
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel =  new ViewModelProvider(this).get(TaskItemViewModel.class);



        //开启新线程解析书籍数据
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        inittaskData();
                        taskAdapter.notifyDataSetChanged();
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                //请求json数据

                getOnlineTasks();

            }
        }.start();



    }

    private void inittaskData(){

        for(int i=0;i<mViewModel.gtbacklist[mPosition].getDataList().size();i++){
            String startDate = ChangeTime.format(String.valueOf(mViewModel.gtbacklist[mPosition].getDataList().get(i).getStartDate()));
            String endDate = ChangeTime.format(String.valueOf(mViewModel.gtbacklist[mPosition].getDataList().get(i).getEndDate()));

            MyTask task = new MyTask();
            task.setTaskTitle(mViewModel.gtbacklist[mPosition].getDataList().get(i).getTitle());
            task.isDone();
            task.setPublisher(mViewModel.gtbacklist[mPosition].getDataList().get(i).getPublisher());
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            tasks.add(task);


        }



    }

    private void getOnlineTasks(){

        mViewModel.getOnlineTask(taskcage[mPosition], new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("error", "onFailure: error");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String res = response.body().string();

                Log.d("mytask",res);

                mViewModel.processData(res,mPosition);

                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);


            }
        });



    }

}
