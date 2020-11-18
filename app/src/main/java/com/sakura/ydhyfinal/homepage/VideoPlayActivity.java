package com.sakura.ydhyfinal.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.sakura.ydhyfinal.MainActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.util.TimerTask;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoPlayViewModel videoPlayViewModel;

    private ProgressBar progressBar;

    private SurfaceView surfaceView;

    private Button button;

    private FrameLayout frameLayout,yF;

    private SeekBar mediaseek;
    boolean flagbtnCorl = false;

    private ImageView btn_states,sizeCtrl;
    boolean hflages = false;

    private ConstraintLayout constraintLayout;

    private Handler mhandler = new Handler();
    private int mCount = 0;

    private TextView name,title;

    //播放0，暂停1，重播2
    int playstates = 0;



    private Runnable mCounter = new Runnable() {
        @Override
        public void run() {
            mCount++;
            UpdateSeekProgress();
            mhandler.postDelayed(this,500);
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.media_conFramelayout:
                    if(flagbtnCorl = true){
                        frameLayout.setVisibility(View.INVISIBLE);
                        flagbtnCorl = false;

                    }else{
                        frameLayout.setVisibility(View.VISIBLE);
                        flagbtnCorl = true;
                    }
                    break;

                case R.id.playerFrame:
                case R.id.surfaceView:

                    frameLayout.setVisibility(View.VISIBLE);
                    flagbtnCorl = true;

                    break;

                case R.id.media_btn_play:
                    if(playstates == 0){
                        videoPlayViewModel.mediaPlayer.pause();
                        btn_states.setImageResource(R.drawable.ic_baseline_play);
                        playstates = 1;
                    }


                    break;

                case R.id.media_btn_big:
                    if(hflages == false){
                        VideoPlayActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        View decorView = getWindow().getDecorView();
                        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
                        hflages = true;


                    }else {
                        VideoPlayActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        hflages = false;
                    }
                    break;
                case R.id.media_back:
                    finish();
                    break;


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {


        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {


        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_play);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        int screenHeight = dm.heightPixels;








//        WindowManager.LayoutParams params = imageView.getLayoutParams();
//        params.height=screenWidth/10;
//        params.width =screenHeight/10;
//        imageView.setLayoutParams(params);




        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        progressBar = findViewById(R.id.progressBar);
        surfaceView = findViewById(R.id.surfaceView);
        frameLayout = findViewById(R.id.media_conFramelayout);
        yF = findViewById(R.id.playerFrame);
        mediaseek = findViewById(R.id.media_seekBar);
        btn_states = findViewById(R.id.media_btn_play);
        sizeCtrl = findViewById(R.id.media_btn_big);
        name = findViewById(R.id.media_auther);
        title = findViewById(R.id.media_titles);

        button = findViewById(R.id.media_back);

        String url = getIntent().getStringExtra("url");

        String meidainfoname = getIntent().getStringExtra("author");
        String meidainfotit = getIntent().getStringExtra("title");



        name.setText(meidainfoname);
        title.setText("《"+meidainfotit+"》");
        //StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));

        videoPlayViewModel = new ViewModelProvider(this).get(VideoPlayViewModel.class);

        videoPlayViewModel.loadVideo(url);

        videoPlayViewModel.getProgressBarvis().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    progressBar.setVisibility(View.INVISIBLE);
                    mediaseek.setMax(videoPlayViewModel.mediaPlayer.getDuration());
                    mhandler.post(mCounter);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        videoPlayViewModel.getPlayStates().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0:
                        btn_states.setClickable(false);
                    case 1:
                        btn_states.setImageResource(R.drawable.ic_baseline_pause);
                        break;
                    case 3:
                        btn_states.setImageResource(R.drawable.ic_baseline_replay);
                        break;
                }

                btn_states.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(integer == 1){
                            videoPlayViewModel.mediaPlayer.pause();
                            btn_states.setImageResource(R.drawable.ic_baseline_play);
                            videoPlayViewModel.getPlayStates().setValue(2);
                        }

                        else if(integer == 2){
                            videoPlayViewModel.mediaPlayer.start();
                            //btn_states.setImageResource();
                            videoPlayViewModel.getPlayStates().setValue(1);
                        }
                        else if(integer == 3){
                            videoPlayViewModel.mediaPlayer.start();
                            videoPlayViewModel.getPlayStates().setValue(1);
                        }

                    }
                });

            }
        });


        videoPlayViewModel.getSeekbarload().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                mediaseek.setSecondaryProgress(mediaseek.getMax() * integer / 100);
            }
        });


        mediaseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    videoPlayViewModel.PlayerSeekToPro(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                videoPlayViewModel.mediaPlayer.setDisplay(holder);
                videoPlayViewModel.mediaPlayer.setScreenOnWhilePlaying(true);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });


        addListener();


    }

    private void UpdateSeekProgress(){

        mediaseek.setProgress(videoPlayViewModel.mediaPlayer.getCurrentPosition());
    }





    private void addListener(){

        frameLayout.setOnClickListener(clickListener);
        surfaceView.setOnClickListener(clickListener);

        yF.setOnClickListener(clickListener);

        btn_states.setOnClickListener(clickListener);

        button.setOnClickListener(clickListener);

        sizeCtrl.setOnClickListener(clickListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayViewModel.mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayViewModel.mediaPlayer.start();
    }



    @Override
    public void finish() {
        super.finish();
        mhandler.removeCallbacks(mCounter);
//        videoPlayViewModel.mediaPlayer.reset();
//        videoPlayViewModel.mediaPlayer.release();
    }
}