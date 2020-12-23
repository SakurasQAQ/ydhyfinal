package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.BookTaskInfoViewModel;
import com.sakura.ydhyfinal.adapter.BookTaskAdapter;
import com.sakura.ydhyfinal.bean.TaskBooks;
import com.sakura.ydhyfinal.databinding.ActivityBookTaskInfoBinding;
import com.sakura.ydhyfinal.utils.ChangeTime;

import java.lang.ref.WeakReference;
import java.security.Provider;
import java.util.ArrayList;

public class BookTaskInfoActivity extends AppCompatActivity {

    ActivityBookTaskInfoBinding binding;

    private SkeletonScreen skeletonScreen;

    BookTaskInfoViewModel mViewmodel;

    private BookTaskAdapter adapter;

    private ArrayList<TaskBooks> list = new ArrayList<>();

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<BookTaskInfoActivity> activityWeakReference;

        MyHandler(BookTaskInfoActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().skeletonScreen.hide();
            }
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.dodgerblue));
        }

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.dodgerblue));

        binding = DataBindingUtil.setContentView(this,R.layout.activity_book_task_info);

        mViewmodel = new ViewModelProvider(this).get(BookTaskInfoViewModel.class);



        binding.bookTaskinfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //取出缓存数据用于接口访问
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userId","");

        String taskid = getIntent().getStringExtra("taskid");

        Log.d("tag", "onCreate: "+userid +"       " +taskid);


        mViewmodel.getOnlineTaskinfo(taskid,userid);

        View rootview =findViewById(R.id.bookTaskroot);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3, RecyclerView.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.bookTaskinfoBooklist.setLayoutManager(layoutManager);





        //骨骼屏预显示
        skeletonScreen = Skeleton.bind(rootview)
                .load(R.layout.skeleton_booksinfo)
                .duration(1000)
                .angle(0)
                .show();



        mViewmodel.getIsgetData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    initView();
                    skeletonScreen.hide();
                }
            }
        });






    }

    private void initView(){
        binding.bookTaskinfoTitle.setText(mViewmodel.getGetTaskInfo().getTaskTitle());
        binding.bookTaskinfoPubliser.setText(mViewmodel.getGetTaskInfo().getPublisher());
        String startTime = ChangeTime.format(String.valueOf(mViewmodel.getGetTaskInfo().getStartDate()));
        binding.bookTaskinfoStartTime.setText("发布于  "+startTime);
        binding.bookTaskinfoContext.setText(mViewmodel.getGetTaskInfo().getDescription());

        String TaskType = mViewmodel.getGetTaskInfo().getDescription().substring(0,mViewmodel.getGetTaskInfo().getDescription().indexOf("|"));
        binding.bookTaskinfoType.setText(TaskType);

        String endTime = ChangeTime.format(String.valueOf(mViewmodel.getGetTaskInfo().getEndDate()));
        binding.bookTaskinfoEndtime.setText("截至时间：  "+endTime);

        boolean isDone = mViewmodel.getGetTaskInfo().isDone();
        if(isDone){
            binding.bookTaskinfoBtnDone.setText("已完成");

        }else {
            binding.bookTaskinfoBtnDone.setText("未完成");

        }

        boolean hasCommend = mViewmodel.getGetTaskInfo().isHasComment();
        if(hasCommend){
            binding.bookTaskinfoBtnHansComm.setText("已评价");
        }else {
            binding.bookTaskinfoBtnHansComm.setText("未评价");
        }

        TaskBooks books;
        for(int i = 0;i<mViewmodel.getGetTaskInfo().getTaskBooks().size();i++){
            books = new TaskBooks();
            books.setId(mViewmodel.getGetTaskInfo().getTaskBooks().get(i).getId());
            books.setTitle(mViewmodel.getGetTaskInfo().getTaskBooks().get(i).getTitle());
            books.setCoverImg(mViewmodel.getGetTaskInfo().getTaskBooks().get(i).getCoverImg());
            books.setNecessary(mViewmodel.getGetTaskInfo().getTaskBooks().get(i).getNecessary());
            list.add(books);
        }

        adapter = new BookTaskAdapter(this,list);

        binding.bookTaskinfoBooklist.setAdapter(adapter);
    }

}