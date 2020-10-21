package com.sakura.ydhyfinal.main.home;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.Activity.BooksFragDetailListActivity;
import com.sakura.ydhyfinal.homepage.MinClassActivity;
import com.sakura.ydhyfinal.Activity.SearchActivity;
import com.sakura.ydhyfinal.LoginActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.HomeBooksListAdapter;
import com.sakura.ydhyfinal.adapter.ImageNetAdapter;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.bean.DataBean;
import com.sakura.ydhyfinal.databinding.FragmentHomeBinding;


import com.sakura.ydhyfinal.homepage.RanksActivity;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment implements OnPageChangeListener{



    private HomeBooksListAdapter adapter;
    private HomeViewModel mViewModel;
    private FragmentHomeBinding binding;

    private Handler mHandler;

    private ArrayList<Booksinfo> bookslist = new ArrayList<>();

    private Boolean islogin;



    private OnMultiClickListener homelistener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.home_nume01:
                    if(islogin){
                        startActivity(new Intent(getContext(), RanksActivity.class));
                    }else{
                        clickNotlogin();
                    }
                    break;
                case R.id.home_nume02:
                    if(islogin){
                        startActivity(new Intent(getContext(), MinClassActivity.class));
                    }else{
                        clickNotlogin();
                    }
                    break;
                case R.id.home_nume03:
                    break;
                case R.id.home_nume04:
                    break;
                case R.id.home_nume05:
                    break;

                case R.id.home_btnmore:

                    Intent intent = new Intent();
                    intent.putExtra("Tilts","热门书籍");
                    intent.putExtra("cages","");
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.CardView:
                    if(islogin){
                        startActivity(new Intent(getContext(), SearchActivity.class));
                    }else{
                        clickNotlogin();
                    }
                    break;
                case R.id.iv_menu:
                    if(islogin){

                    }else{
                        clickNotlogin();
                    }
                    break;
            }
        }
    };


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), getResources().getColor(R.color.white));

        //getActivity().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        binding.swip.setColorSchemeColors(R.color.dodgerblue);


        //判断用户登录状态及头像
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);



        String imgperson = sharedPreferences.getString("userimg","");

        if(!imgperson.isEmpty()){
            Glide.with(getContext())
                    .load(imgperson)
                    .into(binding.ivMenu);
                    islogin = true;

        }else {
            islogin = false;
            binding.ivMenu.setImageResource(R.drawable.login_xuesheng);
        }


        //轮播图
        binding.banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()))
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getContext()))

                .addOnPageChangeListener(this)
                .setOnBannerListener((data, position) -> {
                    Log.d("test", "position：" + position);
                });


        addlisenter();

        //表格
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.homeRecyview.setLayoutManager(layoutManager);
        adapter = new HomeBooksListAdapter(bookslist,getContext(),islogin);

        binding.homeRecyview.setAdapter(adapter);




        return binding.getRoot();
    }







    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);



//        //开启新线程解析首页数据
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        addinfo();
                        adapter.notifyDataSetChanged();
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                String cache = CacheUtils.getCache(getContext(),mViewModel.URL);

                if(!TextUtils.isEmpty(cache)){

                    //有缓存
                    mViewModel.processData(cache);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);

                }else{
                    mViewModel.gethomebooks(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.i("error", "onFailure: error");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            String res = response.body().string();

                            Log.i("result:", res);
                            //解析数据方法
                            mViewModel.processData(res);


                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);

                            Log.d("message", "onResponse: "+message.what);

                            //写一次缓存
                            CacheUtils.setCache(getContext(),mViewModel.URL,res);
                        }
                    }
                );
            }
        }
    }.start();





        // TODO: Use the ViewModel
    }





    private void addlisenter(){
        binding.swip.setOnRefreshListener(()->{
            new Handler().postDelayed(()-> binding.swip.setRefreshing(false),1000);
        });

        binding.CardView.setOnClickListener(homelistener);
        binding.homeNume01.setOnClickListener(homelistener);
        binding.homeNume02.setOnClickListener(homelistener);
        binding.ivMenu.setOnClickListener(homelistener);
        binding.homeBtnmore.setOnClickListener(homelistener);

    }

    private void addinfo(){

        for(int i=0;i < mViewModel.getback.getData().dataList.size();i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getback.getData().dataList.get(i).picUrl);
            bks.setBooksName(mViewModel.getback.getData().dataList.get(i).name);
            bks.setBooksId(mViewModel.getback.getData().dataList.get(i).id);
            bookslist.add(bks);

        }



    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void clickNotlogin(){
        Toast.makeText(getContext(),"未登录，请先登录",Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }, 1500);



    }

}
