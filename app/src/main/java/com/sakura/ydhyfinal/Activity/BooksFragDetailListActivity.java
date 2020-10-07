package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

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

    private ArrayList<MyWorks> booksdetailsList = new ArrayList<>();

    private BooksFragDetailViewModel booksFragDetailViewModel;

    private View.OnClickListener listener = view -> {
        switch (view.getId()){
//            case R.id.booklist_back:
//
//                break;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_books_frag_detail_list);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_books_frag_detail_list);

        booksFragDetailViewModel = new ViewModelProvider(this).get(BooksFragDetailViewModel.class);
        //mainViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);

        String cages = getIntent().getStringExtra("cages");

        booksFragDetailViewModel.getCurrentcage().setValue(cages);


        initView();

        binding.toobars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.booklistsRecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        booksadapter = new Booklistdetailadapter(new DiffUtil.ItemCallback<MyWorks>() {
            @Override
            public boolean areItemsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksId().equals(newItem.getWorksId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull MyWorks oldItem, @NonNull MyWorks newItem) {
                return oldItem.getWorksName().equals(newItem.getWorksName());
            }
        },getApplication());


        booksFragDetailViewModel.getmLiveData().observe(this, myWorks -> {
            booksadapter.submitList(myWorks);
        });

        binding.booklistsRecy.setAdapter(booksadapter);
        booksadapter.notifyDataSetChanged();



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
