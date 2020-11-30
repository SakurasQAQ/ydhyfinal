package com.sakura.ydhyfinal.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sakura.ydhyfinal.R;

public class OrceanWorldActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcean_world);

        webView = findViewById(R.id.OrceanWrold_pager);

        String loadurl = "https://ro.bnuz.edu.cn/ReadingOcean/ocean/newMobileOceanAnimal?userId="+getIntent().getStringExtra("userId");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);

        WebViewClient webViewClient = new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 设置可以支持缩放

        webView.getSettings().setSupportZoom(true);

// 设置出现缩放工具

        webView.getSettings().setBuiltInZoomControls(true);

//扩大比例的缩放
//
        webView.getSettings().setUseWideViewPort(true);

        //自适应屏幕

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.getSettings().setLoadWithOverviewMode(true);


        webView.setWebViewClient(webViewClient);

        webView.loadUrl(loadurl);



    }
}