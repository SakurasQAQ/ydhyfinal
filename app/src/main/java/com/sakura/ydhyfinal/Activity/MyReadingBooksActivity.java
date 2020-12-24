package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.BookTaskInfoViewModel;
import com.sakura.ydhyfinal.ViewModel.ReadingbooksViewModel;
import com.sakura.ydhyfinal.adapter.BookTaskAdapter;
import com.sakura.ydhyfinal.adapter.MyReadingAdapter;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.bean.TaskBooks;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class MyReadingBooksActivity extends AppCompatActivity {

    RecyclerView ReadingRecView;
    Toolbar toolbar;

    TextView tit;

    ReadingbooksViewModel viewModel;

    private MyReadingAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reading_books);

        String type = getIntent().getStringExtra("type");
        String Title = getIntent().getStringExtra("Title");


        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.dodgerblue));

        viewModel = new ViewModelProvider(this).get(ReadingbooksViewModel.class);


        toolbar = findViewById(R.id.ReadingTabars);
        ReadingRecView = findViewById(R.id.ReadingList);
        tit = findViewById(R.id.ReadingTabars_title);

        tit.setText(Title);

        viewModel.getPagecage().setValue(type);


        toolbar.setNavigationOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });


//        viewModel.getOnlinesReadingBooks("reading",userid);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        ReadingRecView.setLayoutManager(gridLayoutManager);

        adapter = new MyReadingAdapter(new DiffUtil.ItemCallback<TaskBooks>() {
            @Override
            public boolean areItemsTheSame(@NonNull TaskBooks oldItem, @NonNull TaskBooks newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TaskBooks oldItem, @NonNull TaskBooks newItem) {
                return oldItem.getCoverImg().equals(newItem.getCoverImg());
            }
        },this);





        viewModel.getmLivedata().observe(this, new Observer<PagedList<TaskBooks>>() {
            @Override
            public void onChanged(PagedList<TaskBooks> taskBooks) {
                if(taskBooks != null){
                    adapter.submitList(taskBooks);

                }
            }
        });

        final SkeletonScreen skeletonScreen = Skeleton.bind(ReadingRecView)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_books)
                .show();


        viewModel.getJudes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    skeletonScreen.hide();
                }
            }
        });






    }



}