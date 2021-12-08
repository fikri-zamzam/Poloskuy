package com.example.apk_poloskuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.apk_poloskuy.myfragment.AllListFragment;
import com.example.apk_poloskuy.myfragment.HomeFragment;
import com.example.apk_poloskuy.myfragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    //deklarasi button kategori
    private CardView kate1, kate2, kate3, kate4;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //memanaggil button kategori sesuai id
        kate1 = findViewById(R.id.kate1);
        kate2 = findViewById(R.id.kate2);
        kate3 = findViewById(R.id.kate3);
        kate4 = findViewById(R.id.kate4);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcont, new HomeFragment()).commit();

        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.all_list:
                        selectedFragment= new AllListFragment();
                        break;

                    case R.id.my_setting:
                        selectedFragment = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragcont, selectedFragment).commit();

                return true;
            }
        });
    }
}