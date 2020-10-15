package com.sakura.ydhyfinal.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.ActivityMinClassBinding;

public class MinClassActivity extends AppCompatActivity {

    private ActivityMinClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_min_class);



    }
}