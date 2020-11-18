package com.sakura.ydhyfinal.homepage;

import android.app.Application;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

public class VideoPlayViewModel extends AndroidViewModel {
    public VideoPlayViewModel(@NonNull Application application) {
        super(application);
    }

    public MediaPlayer mediaPlayer = new MediaPlayer();

    enum PlayStatse{};


    private MutableLiveData<Integer> Seekbarload;
    public MutableLiveData<Integer> getSeekbarload() {
        if(Seekbarload == null){
            Seekbarload = new MutableLiveData<>();
            Seekbarload.setValue(0);
        }

        return Seekbarload;
    }



    private MutableLiveData<Integer> progressBarvis;

    public MutableLiveData<Integer> getProgressBarvis() {
        if(progressBarvis == null){
            progressBarvis = new MutableLiveData<>();
            progressBarvis.setValue(0);
        }

        return progressBarvis;
    }


    private MutableLiveData<Integer> playStates;

    public MutableLiveData<Integer> getPlayStates() {
        if(playStates == null){
            playStates = new MutableLiveData<>();
            playStates.setValue(0);
        }

        return playStates;
    }

    public void loadVideo(String url){

        try {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();

            //视频首次加载监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBarvis.setValue(1);
                    mp.start();
                    playStates.setValue(1);
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(getApplication(),"发生错误"+"  "+what+"  "+extra,Toast.LENGTH_SHORT).show();

                    return false;
                }
            });

            //视频缓冲监听
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Seekbarload.setValue(percent);

                }
            });

            //视频完成播放监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playStates.setValue(3);
                }
            });

            //视频大小区域
            mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                }
            });

            //视频加载条是否加载完成监听
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    mp.start();
                    progressBarvis.setValue(1);
                }
            });




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlayerSeekToPro(int progress){
        progressBarvis.setValue(0);
        mediaPlayer.seekTo(progress);

    }


    @Override
    protected void onCleared() {
        super.onCleared();

        mediaPlayer.release();
    }
}
