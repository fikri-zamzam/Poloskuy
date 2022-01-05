package com.example.apk_poloskuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.apk_poloskuy.Activity.RVBarang;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    private BottomNavigationView menu_bawah;
    private TextView hh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);

        menu_bawah=findViewById(R.id.menu_bawah);

        menu_bawah.setOnNavigationItemReselectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.nav_home:
               Intent intent = new Intent(NavMainActivity.this,KeranjangActivity.class);
               startActivity(intent);
               break;
           case R.id.all_list:
               Intent inte=new Intent(NavMainActivity.this, RVBarang.class);
               startActivity(inte);
               break;
           case R.id.my_setting:
               Intent ente=new Intent(NavMainActivity.this,StatusPengirimanActivity.class);
               startActivity(ente);
               break;
       }
       
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(NavMainActivity.this,KeranjangActivity.class);
                startActivity(intent);
                break;
            case R.id.all_list:
                Intent inte=new Intent(NavMainActivity.this,RVBarang.class);
                startActivity(inte);
                break;
            case R.id.my_setting:
                Intent ente=new Intent(NavMainActivity.this,StatusPengirimanActivity.class);
                startActivity(ente);
                break;
        }

    }
}