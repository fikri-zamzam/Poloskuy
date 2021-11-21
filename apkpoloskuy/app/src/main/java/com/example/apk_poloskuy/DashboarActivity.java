package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class DashboarActivity extends AppCompatActivity {
    //deklarasi button kategori
    private CardView kate1, kate2, kate3, kate4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboar);

        //memanaggil button kategori sesuai id
        kate1 = findViewById(R.id.kate1);
        kate2 = findViewById(R.id.kate2);
        kate3 = findViewById(R.id.kate3);
        kate4 = findViewById(R.id.kate4);
    }
}