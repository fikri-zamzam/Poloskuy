package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apk_poloskuy.Model.ModelBarang;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailBarangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nama,harga,desc;
        ImageView gambar;

        Button btnWa;
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


        //BUtton chat whastapp

        btnWa = findViewById(R.id.buttonChat);
        btnWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link= null;
                try {
                    link = "https://api.whatsapp.com/send?phone=+6281249345670"+"&text=Hallo.\n" +
                            "Saya ingin bertanya ketersediaan item di POLOSKUY!";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Gagal membuka WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

}