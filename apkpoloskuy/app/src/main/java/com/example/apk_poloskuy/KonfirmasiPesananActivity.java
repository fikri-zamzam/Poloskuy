package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Model.ModelStatusKirim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiPesananActivity extends AppCompatActivity {
    Button konfirmasi,kembali;
    String id;
    EditText noteUser;
    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        konfirmasi = findViewById(R.id.btn_ya);
        kembali = findViewById(R.id.btn_tidak);

        noteUser = findViewById(R.id.add_ket);

        Intent intent = getIntent();
        ModelStatusKirim status = intent.getParcelableExtra("STATUS");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = status.getIdTrans();
                String note = noteUser.getText().toString();
                AddKonfirmasi(id,note);
            }
        });

    }

    private void AddKonfirmasi(String id,String note) {
        String idini = id;
        String noteini = note;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "ubahStatus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("kode").equals("1")) {
                                Toast.makeText(KonfirmasiPesananActivity.this, "Terimakasih telah Belanja di Poloskuy! ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KonfirmasiPesananActivity.this,NavMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(KonfirmasiPesananActivity.this, jsonObject.getString("pesan"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KonfirmasiPesananActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_transaksi", idini);
                params.put("note", noteini);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(KonfirmasiPesananActivity.this);
        rQueue.add(stringRequest);

    }

}