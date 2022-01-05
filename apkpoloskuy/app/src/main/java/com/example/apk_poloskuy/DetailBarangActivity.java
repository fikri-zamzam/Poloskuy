package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apk_poloskuy.Model.ModelBarang;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailBarangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nama,harga,desc;
        ImageView gambar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        nama = findViewById(R.id.TxtDetNama);
        harga = findViewById(R.id.TxtDetHarga);
        desc = findViewById(R.id.TxtDetDesc);
        gambar = findViewById(R.id.imgDetPrdk);

        Intent intent = getIntent();
        ModelBarang barang = intent.getParcelableExtra("BARANG");

        nama.setText(barang.getNamaPrdk());
//        harga.setText(Double.toString(barang.getHargaPrdk()));
        harga.setText(Rupiah(barang.getHargaPrdk()));
        desc.setText(barang.getDescPrdk());

        String url2 = "https://ws-tif.com/poloskuy/images/db/produk/"+barang.getImg();
        Glide.with(this)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(gambar);
    }

    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}