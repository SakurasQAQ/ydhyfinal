package com.sakura.ydhyfinal.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.sakura.ydhyfinal.R;

public class CourseWebActivity extends AppCompatActivity {

    private WebView webView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_web);

        String loadurl = getIntent().getStringExtra("url");



        webView = findViewById(R.id.Course_web);
        button = findViewById(R.id.btn_webback);

        //设置支持Javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //

        //优先使用缓存:
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。




        WebViewClient webViewClient = new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


        };


        webView.setWebViewClient(webViewClient);

        webView.loadUrl(loadurl);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();




            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.getSettings().setJavaScriptEnabled(false);
    }

    @Override
    protected void onPause() {
        webView.reload();

        super.onPause();

    }
}