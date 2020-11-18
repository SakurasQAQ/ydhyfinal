package com.sakura.ydhyfinal.main.books;

import androidx.lifecycle.ViewModelProvider;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.navigation.NavigationView;
import com.sakura.ydhyfinal.Activity.BooksFragDetailListActivity;
import com.sakura.ydhyfinal.Activity.SearchActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.adapter.HomeBooksListAdapter;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.databinding.BooksFragmentBinding;
import com.sakura.ydhyfinal.utils.CacheUtils;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BooksFragment extends Fragment {

    private BooksViewModel mViewModel;

    private BooksFragmentBinding binding;

    private Handler mHandler;

    private HomeBooksListAdapter[] myadapter = new HomeBooksListAdapter[10];

    private ArrayList<Booksinfo> bookslist = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist01 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist02 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist03 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist04 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist05 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist06 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist07 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist08 = new ArrayList<>();
    private ArrayList<Booksinfo> bookslist09 = new ArrayList<>();

    private ArrayList[] list = new ArrayList[10];

    private Boolean islogin;

    String[] caches = new String[10];
    int cachesnums=0;




    //分类集合
    final String[] cages = new String[]{"category_shige", "category_kexue","category_manhua","category_tonghua","category_shenhua","category_lishi","category_shuxue","category_xiaoshuo","category_mingzhu","category_mingren"};

    private OnMultiClickListener bookslistener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.bookscg_imgbtn_menu:

                    binding.booksDrawerLayout.openDrawer(binding.navBooks);

                    break;

                case R.id.books_Search:
                    startActivity(new Intent(getContext(),SearchActivity.class));
                    break;

                case R.id.ck_shige:
                    Intent intent = new Intent();
                    intent.putExtra("Tilts","优美诗歌");
                    intent.putExtra("cages",cages[0]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ck_huiben:
                    intent = new Intent();
                    intent.putExtra("Tilts","绘本");
                    intent.putExtra("cages",cages[1]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ck_ziran:

                    intent = new Intent();
                    intent.putExtra("Tilts","自然");
                    intent.putExtra("cages",cages[2]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_tonghua:

                    intent = new Intent();
                    intent.putExtra("Tilts","童话故事");
                    intent.putExtra("cages",cages[3]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ck_shenhua:

                    intent = new Intent();
                    intent.putExtra("Tilts","神话传奇");
                    intent.putExtra("cages",cages[4]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_wenshi:
                    intent = new Intent();
                    intent.putExtra("Tilts","文史");
                    intent.putExtra("cages",cages[5]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_shuxue:
                    intent = new Intent();
                    intent.putExtra("Tilts","数学");
                    intent.putExtra("cages",cages[6]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_xiaoshuo:
                    intent = new Intent();
                    intent.putExtra("Tilts","小说散文");
                    intent.putExtra("cages",cages[7]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_minghzu:
                    intent = new Intent();
                    intent.putExtra("Tilts","世界名著");
                    intent.putExtra("cages",cages[8]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;
                case R.id.ck_zhuanji:
                    intent = new Intent();
                    intent.putExtra("Tilts","名人传记");
                    intent.putExtra("cages",cages[9]);
                    intent.setClass(getContext(),BooksFragDetailListActivity.class);
                    startActivity(intent);

                    break;


            }
        }
    };


    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = BooksFragmentBinding.inflate(inflater);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), getResources().getColor(R.color.white));
        //getActivity().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //菜单及点击事件

        binding.navBooks.setItemIconTintList(null);
        binding.navBooks.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item1:
                        //Toast.makeText(getContext(),"tsts1",Toast.LENGTH_SHORT).show();

                        binding.booksscall.smoothScrollTo(0,binding.LLShige.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item2:
                        binding.booksscall.smoothScrollTo(0,binding.LLHuiben.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;
                    case R.id.menu_item3:
                        binding.booksscall.smoothScrollTo(0,binding.LLZiran.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;
                    case R.id.menu_item4:
                        binding.booksscall.smoothScrollTo(0,binding.LLTonghua.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item5:
                        binding.booksscall.smoothScrollTo(0,binding.LLShenhua.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item6:
                        binding.booksscall.smoothScrollTo(0,binding.LLWenshi.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item7:
                        binding.booksscall.smoothScrollTo(0,binding.LLShuxue.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item8:
                        binding.booksscall.smoothScrollTo(0,binding.LLXiaoshuo.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item9:
                        binding.booksscall.smoothScrollTo(0,binding.LLMingzhu.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;

                    case R.id.menu_item10:
                        binding.booksscall.smoothScrollTo(0,binding.LLZhuanji.getTop());
                        binding.booksDrawerLayout.closeDrawer(binding.navBooks);
                        break;



                }
                return true;
            }
        });

        //判断登录
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usertName","");
        if(username.isEmpty()){
            islogin = false;
        }else {
            islogin = true;
        }

        addlistener();

        loadRecycalView();

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =new ViewModelProvider(this).get(BooksViewModel.class);

        loadOnlineJson();

        // TODO: Use the ViewModel
    }









    private void addlistener(){

        binding.bookscgImgbtnMenu.setOnClickListener(bookslistener);
        binding.booksSearch.setOnClickListener(bookslistener);

        binding.ckShige.setOnClickListener(bookslistener);
        binding.ckHuiben.setOnClickListener(bookslistener);
        binding.ckZiran.setOnClickListener(bookslistener);
        binding.ckTonghua.setOnClickListener(bookslistener);
        binding.ckShenhua.setOnClickListener(bookslistener);
        binding.ckWenshi.setOnClickListener(bookslistener);
        binding.ckShuxue.setOnClickListener(bookslistener);
        binding.ckXiaoshuo.setOnClickListener(bookslistener);
        binding.ckMinghzu.setOnClickListener(bookslistener);
        binding.ckZhuanji.setOnClickListener(bookslistener);
    }

    private void loadRecycalView(){
        //表格形式每行存放2个，垂直排列recycleview中不可滑动
        //item1
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.recyc01.setLayoutManager(layoutManager);
        myadapter[0] = new HomeBooksListAdapter(bookslist01,getContext(),islogin);
        binding.recyc01.setAdapter(myadapter[0]);


        final SkeletonScreen skeletonScreen = Skeleton.bind(binding.recyc01)
                .adapter( myadapter[0])
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(9)
                .load(R.layout.item_skeleton_books)
                .show();

        binding.recyc01.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        },1000);


        //item2
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc02.setLayoutManager(layoutManager1);
        myadapter[1] = new HomeBooksListAdapter(bookslist,getContext(),islogin);
        binding.recyc02.setAdapter(myadapter[1]);

        final SkeletonScreen skeletonScreens = Skeleton.bind(binding.recyc02)
                .adapter(myadapter[1])
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(9)
                .load(R.layout.item_skeleton_books)
                .show();

        binding.recyc02.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreens.hide();
            }
        },1000);



        //item3
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc03.setLayoutManager(layoutManager2);
        myadapter[2] = new HomeBooksListAdapter(bookslist02,getContext(),islogin);
        binding.recyc03.setAdapter(myadapter[2]);

        //item4
        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc04.setLayoutManager(layoutManager3);
        myadapter[3] = new HomeBooksListAdapter(bookslist03,getContext(),islogin);
        binding.recyc04.setAdapter(myadapter[3]);

        //item5
        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc05.setLayoutManager(layoutManager4);
        myadapter[4] = new HomeBooksListAdapter(bookslist04,getContext(),islogin);
        binding.recyc05.setAdapter(myadapter[4]);


        //item6
        GridLayoutManager layoutManager5 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc06.setLayoutManager(layoutManager5);
        myadapter[5] = new HomeBooksListAdapter(bookslist05,getContext(),islogin);
        binding.recyc06.setAdapter(myadapter[5]);


        //item7
        GridLayoutManager layoutManager6 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc07.setLayoutManager(layoutManager6);
        myadapter[6] = new HomeBooksListAdapter(bookslist06,getContext(),islogin);
        binding.recyc07.setAdapter(myadapter[6]);

        //item8
        GridLayoutManager layoutManager7 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc08.setLayoutManager(layoutManager7);
        myadapter[7] = new HomeBooksListAdapter(bookslist07,getContext(),islogin);
        binding.recyc08.setAdapter(myadapter[7]);

        //item9
        GridLayoutManager layoutManager8 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc09.setLayoutManager(layoutManager8);
        myadapter[8] = new HomeBooksListAdapter(bookslist08,getContext(),islogin);
        binding.recyc09.setAdapter(myadapter[8]);


        //item10
        GridLayoutManager layoutManager9 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.recyc10.setLayoutManager(layoutManager9);
        myadapter[9] = new HomeBooksListAdapter(bookslist09,getContext(),islogin);
        binding.recyc10.setAdapter(myadapter[9]);




    }

    private void loadOnlineJson(){

        //开启新线程解析书籍数据
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 10:

                    addinfo();
                    for (int i=0;i<10;i++){
                        myadapter[i].notifyDataSetChanged();
                    }



                break;



            }
            }
        };
        new Thread(){
            @Override
            public void run() {
                //请求所有类型的json数据
                Message message = new Message();
                message.what = 0;


                for (int i = 0; i < 10; i++) {
                    caches[i] = CacheUtils.getCache(getContext(), mViewModel.URL + i);
                    if (!TextUtils.isEmpty(caches[i])) {
                        cachesnums++;
                    }
                }

                if (cachesnums == 10) {
                    for (int j = 0; j < 10; j++) {
                        mViewModel.processData(caches[j], j);

                        message.what++;
                        if (message.what == 10) {
                            mHandler.sendMessage(message);
                        }
                    }
                } else {



                for (int i = 0; i < cages.length; i++) {
                    int nums = i;
                    Log.d("nums", "run: " + nums);
                    mViewModel.getBookslistinfo(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.i("error", "onFailure: error");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            String res = response.body().string();

                            mViewModel.processData(res, nums);

                            CacheUtils.setCache(getContext(), mViewModel.URL + nums, res);


                            message.what++;
                            if (message.what == 10) {
                                mHandler.sendMessage(message);
                            }





                        }
                    }, cages[nums]);


                }
            }






            }
        }.start();
    }


    private void addinfo(){



        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[0].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[0].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[0].dataList.get(i).getId());
            bookslist01.add(bks);
        }




        for(int i=0;i<mViewModel.getbacklist[1].dataList.size();i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[1].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[1].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[1].dataList.get(i).getId());
            bookslist.add(bks);
        }
        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[2].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[2].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[2].dataList.get(i).getId());
            bookslist02.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[3].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[3].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[3].dataList.get(i).getId());
            bookslist03.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[4].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[4].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[4].dataList.get(i).getId());
            bookslist04.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[5].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[5].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[5].dataList.get(i).getId());
            bookslist05.add(bks);
        }

        for(int i=0;i<mViewModel.getbacklist[6].dataList.size();i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[6].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[6].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[6].dataList.get(i).getId());
            bookslist06.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[7].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[7].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[7].dataList.get(i).getId());
            bookslist07.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[8].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[8].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[8].dataList.get(i).getId());
            bookslist08.add(bks);
        }

        for(int i=0;i<9;i++){
            Booksinfo bks = new Booksinfo();
            bks.setBooksimgurl(mViewModel.getbacklist[9].dataList.get(i).getCoverImg());
            bks.setBooksName(mViewModel.getbacklist[9].dataList.get(i).getTitle());
            bks.setBooksId(mViewModel.getbacklist[9].dataList.get(i).getId());
            bookslist09.add(bks);
        }



    }




}
