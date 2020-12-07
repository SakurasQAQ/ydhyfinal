package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.SearchBookViewModel;
import com.sakura.ydhyfinal.ViewModel.SearchViewModel;
import com.sakura.ydhyfinal.adapter.QuesSearchbookAdapter;
import com.sakura.ydhyfinal.adapter.SearchBookAdapter;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.databinding.ActivitySubStoryQuesBinding;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class SubStoryQuesActivity extends AppCompatActivity {

    private ActivitySubStoryQuesBinding binding;

    private SearchBookViewModel mViewModle;

    private String keywords = "";

    private SearchBookAdapter adapter;

    private ArrayList<Booksinfo> list = new ArrayList<>();

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener(){
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    if(keywords.equals("")){
                        Toast.makeText(SubStoryQuesActivity.this,"请输入书籍关键字！！",Toast.LENGTH_SHORT).show();
                        list.clear();
                    }else{
                        mViewModle.GetSearchBookslist(keywords);
                    }
                    return false;

            }
            return true;
        }
    };



    private OnMultiClickListener clickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {

            switch (v.getId()){

                case R.id.quessub_btn_back:
                    finish();
                    break;

                case R.id.quessub_btnsearch:

                    if(keywords.equals("")){
                        Toast.makeText(SubStoryQuesActivity.this,"请输入书籍关键字！！",Toast.LENGTH_SHORT).show();
                        list.clear();
                    }else{

                       mViewModle.GetSearchBookslist(keywords);

                    }
                    break;

                case R.id.quessub_bookname_chooes_close:
                    binding.quessubBooknameblock.setVisibility(View.GONE);
                    binding.quessubBooksearchblock.setVisibility(View.VISIBLE);
                    binding.quessubBookslist.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            keywords = s.toString();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_bottom, R.anim.no_slide);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_sub_story_ques);

        mViewModle = new ViewModelProvider(this).get(SearchBookViewModel.class);
        addlistener();

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        binding.quessubBookslist.setLayoutManager(linearLayoutManager);


        adapter = new SearchBookAdapter(this,list,1);

        mViewModle.getLLlist().observe(this, new Observer<List<Booksinfo>>() {
            @Override
            public void onChanged(List<Booksinfo> booksinfos) {
                list.clear();
                for(int i =0;i<booksinfos.size();i++){
                    list.add(booksinfos.get(i));
                }


                adapter.notifyDataSetChanged();
            }
        });

        mViewModle.getExsitnone().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SubStoryQuesActivity.this,"查无此书籍，请更换关键字后重试",Toast.LENGTH_SHORT).show();
                }
            }
        });



        binding.quessubBookslist.setAdapter(adapter);

        adapter.setmClickListener(new SearchBookAdapter.SpecClickListener() {
            @Override
            public void onitemClick(String booksname, int position) {
                Toast.makeText(SubStoryQuesActivity.this," "+booksname,Toast.LENGTH_SHORT).show();
                binding.quessubBooksearchblock.setVisibility(View.GONE);
                binding.quessubBookslist.setVisibility(View.GONE);
                list.clear();

                binding.quessubGetbook.setText("");

                binding.quessubBooknameblock.setVisibility(View.VISIBLE);
                binding.quessubBooknameChooes.setText(booksname);

            }
        });



    }


    private void addlistener(){

        binding.quessubBtnBack.setOnClickListener(clickListener);



        binding.quessubGetbook.addTextChangedListener(watcher);

        binding.quessubBtnsearch.setOnClickListener(clickListener);

        binding.quessubGetbook.setOnEditorActionListener(onEditorActionListener);

        binding.quessubBooknameChooesClose.setOnClickListener(clickListener);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_bottom);

    }






}