package com.sakura.ydhyfinal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.gsonres.Get_MSG;
import com.sakura.ydhyfinal.gsonres.Get_MSGSP;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.HashMap;


public class FeedbackActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    private Toolbar toolbar;

    private Get_MSGSP msg;

    private Handler handler = new Handler();

    private View.OnClickListener listener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {

            switch (v.getId()){
                case R.id.feedback_subbtn:
                    getssubmit(editText.getText().toString());
                    Toast.makeText(FeedbackActivity.this,"提交中。。。",Toast.LENGTH_SHORT).show();
                break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.dodgerblue));

        button = findViewById(R.id.feedback_subbtn);
        editText = findViewById(R.id.feedback_maintxt);

        toolbar = findViewById(R.id.feedback_toolbar4);











        addListener();


    }

    private void getssubmit(String str){

        HashMap map = new HashMap();
        map.put("feedback",str);

        RequestManager requestManager = RequestManager.getInstance(this);

        requestManager.requestAsyn("mobileUser/sendFeedback", 0, map, new RequestManager.ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                msg = gson.fromJson(result,Get_MSGSP.class);

                if(msg.getMessage().equals("意见反馈已成功提交")){
                    Toast.makeText(FeedbackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);

                }



            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    private void addListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(listener);


    }
}