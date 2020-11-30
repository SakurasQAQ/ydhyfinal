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
import com.sakura.ydhyfinal.dialogView.ParentReadingDialog;
import com.sakura.ydhyfinal.homepage.MinClassActivity;
import com.sakura.ydhyfinal.Activity.SearchActivity;
import com.sakura.ydhyfinal.LoginActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.HomeBooksListAdapter;
import com.sakura.ydhyfinal.adapter.ImageNetAdapter;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.bean.DataBean;
import com.sakura.ydhyfinal.databinding.FragmentHomeBinding;


import com.sakura.ydhyfinal.homepage.OrceanWorldActivity;
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
                    if(islogin){
                        ParentReadingDialog dialog = new ParentReadingDialog(getContext(),R.style.Dialog_Msg);
                        dialog.show();
                    }else{
                        clickNotlogin();
                    }
                    break;
                case R.id.home_nume05:
                    if(islogin){
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId","");
                        Intent intent = new Intent();
                        intent.putExtra("userId",userId);
                        intent.setClass(getContext(), OrceanWorldActivity.class);

                        startActivity(intent);
                    }else{
                        clickNotlogin();
                    }

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

                case R.id.home_booksbk01:

                    Intent intentx = new Intent();
                    intentx.putExtra("Tilts","优美诗歌");
                    intentx.putExtra("cages","category_shige");
                    intentx.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx);

                    break;
                case R.id.home_booksbk02:

                    Intent intentx1 = new Intent();
                    intentx1.putExtra("Tilts","童话故事");
                    intentx1.putExtra("cages","category_tonghua");
                    intentx1.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx1);

                    break;
                case R.id.home_booksbk03:

                    Intent intentx2 = new Intent();
                    intentx2.putExtra("Tilts","神话传奇");
                    intentx2.putExtra("cages","category_shenhua");
                    intentx2.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx2);

                    break;
                case R.id.home_booksbk04:

                    Intent intentx3 = new Intent();
                    intentx3.putExtra("Tilts","小说散文");
                    intentx3.putExtra("cages","category_xiaoshuo");
                    intentx3.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx3);

                    break;
                case R.id.home_booksbk05:

                    Intent intentx4 = new Intent();
                    intentx4.putExtra("Tilts","世界名著");
                    intentx4.putExtra("cages","category_mingzhu");
                    intentx4.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx4);

                    break;
                case R.id.home_booksbk06:

                    Intent intentx5 = new Intent();
                    intentx5.putExtra("Tilts","名人传记");
                    intentx5.putExtra("cages","category_mingren");
                    intentx5.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intentx5);

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

        binding.homeBooksbk01.setOnClickListener(homelistener);
        binding.homeBooksbk02.setOnClickListener(homelistener);
        binding.homeBooksbk03.setOnClickListener(homelistener);
        binding.homeBooksbk04.setOnClickListener(homelistener);
        binding.homeBooksbk05.setOnClickListener(homelistener);
        binding.homeBooksbk06.setOnClickListener(homelistener);

        binding.homeNume05.setOnClickListener(homelistener);

        binding.homeNume04.setOnClickListener(homelistener);


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
