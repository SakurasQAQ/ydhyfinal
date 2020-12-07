package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.BR;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.SearchBookViewModel;
import com.sakura.ydhyfinal.adapter.SearchBookAdapter;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.databinding.ActivitySearchBinding;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    private SearchBookAdapter adapter;


    private SearchBookViewModel mViewModel;

    private ArrayList<Booksinfo> list = new ArrayList<>();




    private String keywords = "";

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener(){
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    if(keywords.equals("")){
                        Toast.makeText(SearchActivity.this,"请输入书籍关键字！！",Toast.LENGTH_SHORT).show();
                        list.clear();
                    }else{
                        mViewModel.GetSearchBookslist(keywords);
                    }
                    return false;

            }
            return true;
        }
    };


    private OnMultiClickListener listener =new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.searchpage_back:
                    finish();
                    break;
                case R.id.btn_search:

                    if(keywords.equals("")){
                        Toast.makeText(SearchActivity.this,"请输入书籍关键字！！",Toast.LENGTH_SHORT).show();
                        list.clear();

                    }else{

                        mViewModel.GetSearchBookslist(keywords);

                    }
                    break;
                case R.id.clear_allttt:
                    binding.SearchpageInputbar.setText("");
                    break;
            }
        }
    };


    private TextWatcher txtWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            keywords = editable.toString();

            if (binding.SearchpageInputbar.getEditableText().length() >= 1){
                binding.clearAllttt.setVisibility(View.VISIBLE);
            } else{
                binding.clearAllttt.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);

        mViewModel = new ViewModelProvider(this).get(SearchBookViewModel.class);



        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);


        binding.searchRecy.setLayoutManager(gridLayoutManager);

        adapter = new SearchBookAdapter(SearchActivity.this,list);

        binding.searchRecy.setAdapter(adapter);

        mViewModel.getLLlist().observe(this, new Observer<List<Booksinfo>>() {
            @Override
            public void onChanged(List<Booksinfo> booksinfos) {
                list.clear();
                for(int i =0;i<booksinfos.size();i++){
                    list.add(booksinfos.get(i));
                }


                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getExsitnone().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SearchActivity.this,"查无此书籍，请更换关键字后重试",Toast.LENGTH_SHORT).show();
                }
            }
        });




        addlistener();
    }

    private void addlistener(){

        binding.SearchpageInputbar.addTextChangedListener(txtWatch);

        binding.searchpageBack.setOnClickListener(listener);
        binding.btnSearch.setOnClickListener(listener);

        binding.SearchpageInputbar.setOnEditorActionListener(onEditorActionListener);

        binding.clearAllttt.setOnClickListener(listener);
    }


}
