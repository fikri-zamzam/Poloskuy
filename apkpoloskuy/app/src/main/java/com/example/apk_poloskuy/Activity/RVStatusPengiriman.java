package com.example.apk_poloskuy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Adapter.AdapterStatusKirim;
import com.example.apk_poloskuy.CheckoutActivity;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.example.apk_poloskuy.MainActivitySetting;
import com.example.apk_poloskuy.Model.ModelStatusKirim;
import com.example.apk_poloskuy.NavMainActivity;
import com.example.apk_poloskuy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class RVStatusPengiriman extends AppCompatActivity {
    List<ModelStatusKirim> postStatus=new ArrayList<>();
    String url="https://ws-tif.com/poloskuy/system/API/pantauStatus.php";
    AdapterStatusKirim adapter;
    List<ModelStatusKirim> filterList=new ArrayList<>();
    RecyclerView recyclerView;
    EditText search;
    BottomNavigationView btnBawahRV;
    private SharedPrefrencesHelper sharedPrefrencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_status_pengiriman);

        sharedPrefrencesHelper = new SharedPrefrencesHelper(RVStatusPengiriman.this);
        recyclerView=findViewById(R.id.RVPesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        GetData();
    }

    private void GetData() {
        String id_user = sharedPrefrencesHelper.getId_user();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url+"?id_user="+id_user, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<=response.length();i++){
                    try {
                        JSONObject jsonObject =response.getJSONObject(i);
                        postStatus.add(new ModelStatusKirim(
                                jsonObject.getString("namaBrg"),
                                jsonObject.getString("img"),
                                jsonObject.getString("jenis_status"),
                                jsonObject.getString("id_transaksi"),
                                jsonObject.getString("id_detail_trans"),
                                jsonObject.getString("kuantitas"),
                                jsonObject.getDouble("total_harga")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter=new AdapterStatusKirim(getApplicationContext(),postStatus);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(RVStatusPengiriman.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RVStatusPengiriman.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}