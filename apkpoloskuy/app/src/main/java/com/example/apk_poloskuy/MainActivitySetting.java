package com.example.apk_poloskuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apk_poloskuy.Activity.RVBarang;
import com.example.apk_poloskuy.Activity.RVStatusPengiriman;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivitySetting extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView btnNavS;
    Button cek;

    private EditText SFemail, SFpass, SFnama, SFno_telp, SFgender,SFalamat;
    private Button Logout;
    private SharedPrefrencesHelper sharedPrefrencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);
        btnNavS = findViewById(R.id.menu_bawahS);
        btnNavS.setOnNavigationItemSelectedListener(this);

        SFemail = findViewById(R.id.email);
        SFpass  = findViewById(R.id.pass);
        SFnama =  findViewById(R.id.nama);
        SFno_telp = findViewById(R.id.no_telp);
        SFgender = findViewById(R.id.gender);
        SFalamat = findViewById(R.id.alamat);

        Logout = findViewById(R.id.btn_logout);
        sharedPrefrencesHelper = new SharedPrefrencesHelper(MainActivitySetting.this);
        // Verifikasi apakah user sudah login atau belum
        String status = sharedPrefrencesHelper.getStatus();
        if (status.equals("0")) {
            Intent intent = new Intent(MainActivitySetting.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            finish();
        }

        //Mengambil data dari akun user
        SFemail.setText(sharedPrefrencesHelper.getEmail());
        SFnama.setText(sharedPrefrencesHelper.getFullname());
        SFno_telp.setText(sharedPrefrencesHelper.getNomorTelfon());
        SFgender.setText(sharedPrefrencesHelper.getGender());
        SFalamat.setText(sharedPrefrencesHelper.getAlamat());

        // do code here
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrencesHelper.setEmail(null);
                sharedPrefrencesHelper.setPassword(null);
                sharedPrefrencesHelper.setFullname(null);
                sharedPrefrencesHelper.setNomorTelfon(null);
                sharedPrefrencesHelper.setGender(null);
                sharedPrefrencesHelper.setAlamat(null);
                startActivity(new Intent(MainActivitySetting.this, LoginActivity.class));
            }
        });

        cek = findViewById(R.id.btn_cekBelanja);
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySetting.this, RVStatusPengiriman.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(MainActivitySetting.this,NavMainActivity.class);
                startActivity(intent);
                break;
            case R.id.all_list:
                Intent inte=new Intent(MainActivitySetting.this, RVBarang.class);
                startActivity(inte);
                break;
            case R.id.my_setting:
                Intent ente=new Intent(MainActivitySetting.this,MainActivitySetting.class);
                startActivity(ente);
                break;
        }
        return true;
    }
}