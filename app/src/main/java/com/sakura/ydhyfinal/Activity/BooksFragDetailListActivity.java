package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.BooksFragDetailViewModel;
import com.sakura.ydhyfinal.adapter.Booklistdetailadapter;

import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.databinding.ActivityBooksFragDetailListBinding;
import com.sakura.ydhyfinal.utils.EndlessRecyclerOnScrollListener;


import java.util.ArrayList;


public class BooksFragDetailListActivity extends AppCompatActivity {

    private ActivityBooksFragDetailListBinding binding;

    private Booklistdetailadapter booksadapter;
    private BooksFragDetailViewModel booksFragDetailViewModel;




    private boolean flags = false;


    private Boolean islogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_books_frag_detail_list);



        booksFragDetailViewModel = new ViewModelProvider(this).get(BooksFragDetailViewModel.class);


        String cages = getIntent().getStringExtra("cages");

        booksFragDetailViewModel.getCurrentcage().setValue(cages);


        initView();

        //判断登录
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usertName","");
        if(username.isEmpty()){
            islogin = false;
        }else {
            islogin = true;
        }

        binding.toobars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.booklistsRecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return flags;
            }

        });


        booksadapter = new Booklistdetailadapter(new DiffUtil.ItemCallback<MyWorks>() {
            @Override
            public boolean areItemsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksId().equals(newItem.getWorksId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksName().equals(newItem.getWorksName());
            }
        },getApplication(),islogin);


        booksFragDetailViewModel.getmLiveData().observe(this, myWorks -> {
            booksadapter.submitList(myWorks);
        });



        final SkeletonScreen skeletonScreen = Skeleton.bind(binding.booklistsRecy)
                .adapter(booksadapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_bookslists)
                .show();


//        binding.booklistsRecy.setAdapter(booksadapter);
//        booksadapter.notifyDataSetChanged();

        booksFragDetailViewModel.getIsgetdata().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){

                    skeletonScreen.hide();
                    flags = true;
                }
            }
        });



        String tits = getIntent().getStringExtra("Tilts");
        binding.colltit.setTitle(tits);







        //点击事件监听
        addlistener();
    }

    private void initView(){



    }







    private void addlistener(){
        //binding.booklistBack.setOnClickListener(listener);

    }


}
