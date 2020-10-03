package com.sakura.ydhyfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;




public class BottomNavActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener bottomlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

            }
            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.Nav_fragment);
//        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.pager1fragment,R.id.pager2fragment,R.id.pager3fragment,R.id.pager4fragment,R.id.pager5fragment).build();


//        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        //增加监听事件禁止选中状态再次点击

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomlistener);



    }
}
