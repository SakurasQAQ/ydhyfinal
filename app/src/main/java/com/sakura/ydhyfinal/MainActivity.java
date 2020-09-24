package com.sakura.ydhyfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sakura.ydhyfinal.utils.StatusBar;


/*
 *adb connect 127.0.0.1:7555
 * */
public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBar.fitSystemBar(this);
        StatusBar.lightStatusBar(this, true);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, BottomNavActivity.class));
            MainActivity.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
