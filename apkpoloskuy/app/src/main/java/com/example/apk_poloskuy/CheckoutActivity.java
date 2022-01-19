package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Activity.RVBarang;
import com.example.apk_poloskuy.Konek.DbContract;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.example.apk_poloskuy.Konek.VolleyConnection;
import com.example.apk_poloskuy.Model.ModelBarang;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    TextView totalBayar,alamat;
    Button selesai, chat;
    ImageButton btnBack;
    ProgressDialog progressDialog;

    private RequestQueue rQueue;
    private SharedPrefrencesHelper sharedPrefrencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        totalBayar = findViewById(R.id.TVtotal_bayar_ckt);
        alamat = findViewById(R.id.TValamat_ckt);
        selesai = findViewById(R.id.btnfinish);
        chat=findViewById(R.id.btnChatdiChck);
        btnBack=findViewById(R.id.arrowBacktoDtl);

        progressDialog = new ProgressDialog(CheckoutActivity.this);
        sharedPrefrencesHelper = new SharedPrefrencesHelper(CheckoutActivity.this);
        Intent intent = getIntent();
        ModelBarang barang = intent.getParcelableExtra("BARANG");
        alamat.setText(sharedPrefrencesHelper.getAlamat());
        totalBayar.setText(Rupiah(barang.getHargaPrdk()));

        //taruh button wa disini

        // membuat sebuah transaksi
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_user = sharedPrefrencesHelper.getId_user();
                String id_produk = barang.getIdPrdk();

                int ubah = (int) Math.round(barang.getHargaPrdk());
                String total_harga = Integer.toString(ubah);

                AddTransaksi(id_user,id_produk,total_harga);


            }



        });
        //kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kembali = new Intent(CheckoutActivity.this, RVBarang.class);
                startActivity(kembali);
            }
        });

        //konfirmasi pesanan
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkKonfirmasi= null;
                try {
                    linkKonfirmasi = "https://api.whatsapp.com/send?phone=+6281249345670"+"&text=Hallo.\n" +
                            "Saya ingin mengkonfirmasi tatacara pembayaran untuk item *"+ barang.getNamaPrdk()+ "* di POLOSKUY!";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkKonfirmasi));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Gagal membuka WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AddTransaksi(String id_user, String id_produk, String total_harga) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "transaksi.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("1")) {

                                Toast.makeText(CheckoutActivity.this, "Transaksi Berhasil! ", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CheckoutActivity.this,NavMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CheckoutActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckoutActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("id_produk", id_produk);
                params.put("total_harga", total_harga);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(CheckoutActivity.this);
        rQueue.add(stringRequest);

    }


    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}