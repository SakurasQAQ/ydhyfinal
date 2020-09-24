package com.sakura.ydhyfinal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    private View.OnClickListener listener = view -> {
      switch (view.getId()){
          case R.id.searchpage_back:
              finish();
              break;
      }

    };


    private TextWatcher txtWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            binding.txttest.setText(binding.SearchpageInputbar.getText().toString().trim());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);

        addlistener();
    }

    private void addlistener(){

        binding.SearchpageInputbar.addTextChangedListener(txtWatch);

        binding.searchpageBack.setOnClickListener(listener);
    }
}
