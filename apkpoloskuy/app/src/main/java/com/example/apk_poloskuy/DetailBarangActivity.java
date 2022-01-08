package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apk_poloskuy.Activity.RVBarang;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.example.apk_poloskuy.Model.ModelBarang;
//import com.example.apk_poloskuy.myfragment.SettingFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailBarangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nama,harga,desc,stok;
        ImageView gambar;
        Button btnWa, btnBeli;
        ImageButton btnBack;

        SharedPrefrencesHelper sharedPrefrencesHelper;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        nama = findViewById(R.id.TxtDetNama);
        harga = findViewById(R.id.TxtDetHarga);
        desc = findViewById(R.id.TxtDetDesc);
        gambar = findViewById(R.id.imgDetPrdk);
        btnBack = findViewById(R.id.backTolist);
        stok   = findViewById(R.id.myStok);

        Intent intent = getIntent();
        ModelBarang barang = intent.getParcelableExtra("BARANG");

        nama.setText(barang.getNamaPrdk());
        harga.setText(Rupiah(barang.getHargaPrdk()));
        desc.setText(barang.getDescPrdk());
        stok.setText(barang.getStokPrdk());

        String url2 = "https://ws-tif.com/poloskuy/images/db/produk/"+barang.getImg();
        Glide.with(this)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(gambar);


        //BUtton chat whastapp

        btnWa = findViewById(R.id.buttonChat);
        btnWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link= null;
                try {
                    link = "https://api.whatsapp.com/send?phone=+6281249345670"+"&text=Hallo.\n" +
                            "Saya ingin bertanya ketersediaan item *"+ barang.getNamaPrdk()+ "* di POLOSKUY!";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Gagal membuka WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sharedPrefrencesHelper = new SharedPrefrencesHelper(DetailBarangActivity.this);
        String status = sharedPrefrencesHelper.getStatus();
        btnBeli = findViewById(R.id.btnBeli);
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("0")) {
                    Intent intent = new Intent(DetailBarangActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(DetailBarangActivity.this, CheckoutActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                    intent.putExtra("BARANG", barang);
                    startActivity(intent);
                    finish();
                }
            }
        });


        //back to list
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backList = new Intent(DetailBarangActivity.this, RVBarang.class);
                startActivity(backList);
            }
        });

    }

    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}