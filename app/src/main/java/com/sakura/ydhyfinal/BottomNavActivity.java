package com.sakura.ydhyfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuItemView;

public class BottomNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.Nav_fragment);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.pager1fragment,R.id.pager2fragment,R.id.pager3fragment,R.id.pager4fragment,R.id.pager5fragment).build();


        //NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}
