package com.sakura.ydhyfinal.dialogView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.ViewModel.AnimalChooseViewModel;
import com.sakura.ydhyfinal.adapter.AnimalImgsAdapter;
import com.sakura.ydhyfinal.bean.Animals;
import com.sakura.ydhyfinal.gsonres.Get_Animals;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalImgshowDialog extends Dialog {
    public AnimalImgshowDialog(@NonNull Context context) {
        super(context);
    }

    private String uid;
    private List<Animals> listall;

    public AnimalImgshowDialog(@NonNull Context context, int themeResId, String uid, List<Animals> listall) {
        super(context, themeResId);

        this.uid = uid;

        this.listall = listall;
    }



    private LinearLayout btn_l,btn_r;

    private RecyclerView recyclerView;

    private AnimalImgsAdapter imgsAdapter;

    private AnimalChooseViewModel viewModel;

    private ArrayList<Animals> mylist = new ArrayList<>();

    private Get_Animals animals;

    private Handler mHandler;

    private DialogContent dialogContent;


    private OnMultiClickListener listener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.chimg_btn_false:
                    dismiss();
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_imgshows);
        setCanceledOnTouchOutside(false);

        LoadList(listall);


        //LoadImgOnline();

//        mHandler = new Handler(){
//
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//
//                switch (msg.what){
//                    case 1:
//                        imgsAdapter.notifyDataSetChanged();
//
//                        //recyclerView.setAdapter(imgsAdapter);
//                }
//            }
//
//        };
//
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                LoadImgOnline();
//            }
//        };


        //界面初始化布局
        initView();

    }

    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= 1020;
        layoutParams.height= 1800;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x=0;
        layoutParams.y=50;
        //getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    private void initView(){

        btn_r = findViewById(R.id.chimg_btn_false);


        btn_r.setOnClickListener(listener);


        recyclerView = findViewById(R.id.chimg_recy);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);



        imgsAdapter = new AnimalImgsAdapter(getContext(),mylist);

        imgsAdapter.SetAdapterOnClicklistener(new AnimalImgsAdapter.AdapterOnClicklistener() {
            @Override
            public void OnImgClick(int pos) {
                Log.d("点了当前的第", "OnImgClick: "+pos);
                dialogContent.ImgClicks(pos);
            }




        });

        recyclerView.setAdapter(imgsAdapter);

//        AnimalImgsAdapter adapterlis = new AnimalImgsAdapter(getContext(),mylist);



    }



    private void LoadImgOnline(){

        HashMap map = new HashMap();

        map.put("userId",uid);
        for(int j = 1;j<=7;j++){
            int nums = j;

            map.put("pageNum",String.valueOf(j));

            RequestManager requestManager = RequestManager.getInstance(getContext());

            requestManager.requestAsyn("/mobileCreature/getCreatures", 0, map, new RequestManager.ReqCallBack<Object>() {
                @Override
                public void onReqSuccess(Object result) {
                    Gson gson = new Gson();
                    animals = gson.fromJson(String.valueOf(result), Get_Animals.class);

                    //设置图片存放数组
                    for(int i = 0;i<animals.getDataList().size();i++){
                        Animals animalsinfo = new Animals();
                        animalsinfo.setImg(animals.getDataList().get(i).getImg());
                        animalsinfo.setId(animals.getDataList().get(i).getId());
                        mylist.add(animalsinfo);

                    }

                    Log.d("showjieguo1", "onReqSuccess: ");

                    if (nums == 7) {
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                        Log.d("mynum", "onReqSuccess: "+nums);
                    }



                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            });


        }


    }

    public interface DialogContent{

        public void ImgClicks(int pos);
    }

    public void SetDialogContent(DialogContent dialogContent){
        this.dialogContent = dialogContent;

    }

    private void LoadList(List<Animals> myalllist){
        for(int i = 0;i<myalllist.size();i++){
            Animals animalsinfo = new Animals();
            animalsinfo.setImg(myalllist.get(i).getImg());
            animalsinfo.setId(myalllist.get(i).getId());
            mylist.add(animalsinfo);

        }


    }






}
