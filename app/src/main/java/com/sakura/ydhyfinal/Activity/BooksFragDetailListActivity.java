package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
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
            case R.id.booklist_back:
                finish();
                break;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_books_frag_detail_list);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_books_frag_detail_list);

        booksFragDetailViewModel = new ViewModelProvider(this).get(BooksFragDetailViewModel.class);

        String cages = getIntent().getStringExtra("cages");

        booksFragDetailViewModel.getOnlineData(cages);

        booksFragDetailViewModel.getJudes().observe(this,(integer -> {

            if(integer != 0){
                addintolist();

                booksadapter.notifyDataSetChanged();
            }

        }));

        //请求数据
        initView();

        //点击事件监听
        addlistener();
    }

    private void initView(){

        binding.booklistsRecy.setLayoutManager(new LinearLayoutManager(getApplication()));
        booksadapter = new Booklistdetailadapter(booksdetailsList,getApplication());
        binding.booklistsRecy.setAdapter(booksadapter);

        String tits = getIntent().getStringExtra("Tilts");
        binding.bookslistsTit.setText(tits);

    }



    private void addintolist(){
        MyWorks wks;
        for(int i = 0;i<booksFragDetailViewModel.getbacklist.dataList.size();i++){
            wks = new MyWorks();
            wks.setWorksName(booksFragDetailViewModel.getbacklist.dataList.get(i).getTitle());
            wks.setWorksAuthor(booksFragDetailViewModel.getdetails[i].getData().getAuthor());
            wks.setWorksPicUrl(booksFragDetailViewModel.getbacklist.dataList.get(i).getCoverImg());
            wks.setWorksIntroduction(booksFragDetailViewModel.getdetails[i].getData().getIntroduction());
            booksdetailsList.add(wks);
        }

    }

//    private void addintoperlist(){
//        MyWorks wks = new MyWorks();
//        wks.setWorksName("test");
//        wks.setWorksAuthor("test");
//        wks.setWorksIntroduction("showtest");
//        booksdetailsList.add(w)
//
//    }


    private void addlistener(){
        binding.booklistBack.setOnClickListener(listener);
//        binding.booklistsRecy.addOnScrollListener(new EndlessRecyclerOnScrollListener(){
//            @Override
//            public void onLoadMore() {
//
//
//
//                booksadapter.updateData(booksdetailsList);
//            };
//
//        });
    }


}
