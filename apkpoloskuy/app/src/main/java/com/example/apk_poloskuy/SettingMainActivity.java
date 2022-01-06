package com.example.apk_poloskuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.apk_poloskuy.Activity.RVBarang;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
private BottomNavigationView buttonBawah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);


        buttonBawah=findViewById(R.id.menu_bawahS);
        buttonBawah.setOnNavigationItemReselectedListener(this);
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(SettingMainActivity.this,NavMainActivity.class);
                startActivity(intent);
                break;
            case R.id.all_list:
                Intent inte=new Intent(SettingMainActivity.this, RVBarang.class);
                startActivity(inte);
                break;
            case R.id.my_setting:
                Intent ente=new Intent(SettingMainActivity.this,SettingMainActivity.class);
                startActivity(ente);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(SettingMainActivity.this,NavMainActivity.class);
                startActivity(intent);
                break;
            case R.id.all_list:
                Intent inte=new Intent(SettingMainActivity.this, RVBarang.class);
                startActivity(inte);
                break;
            case R.id.my_setting:
                Intent ente=new Intent(SettingMainActivity.this,SettingMainActivity.class);
                startActivity(ente);
                break;
        }


        return true;
    }
}