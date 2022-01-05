package com.example.apk_poloskuy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Adapter.AdapterBarang;
import com.example.apk_poloskuy.Model.ModelBarang;
import com.example.apk_poloskuy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class RVBarang extends AppCompatActivity {
    List<ModelBarang> postList=new ArrayList<>();
    String url="https://ws-tif.com/poloskuy/system/API/taufiq//listProdukSaya.php";
    AdapterBarang adapter;
    List<ModelBarang> filterList=new ArrayList<>();
    RecyclerView recyclerView;
    EditText search;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_barang);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        GetData();
    }

    private void GetData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<=response.length();i++){
                    try {
                        JSONObject jsonObject =response.getJSONObject(i);
                        postList.add(new ModelBarang(

                                jsonObject.getString("img"),
                                jsonObject.getString("nama_produk"),
                                jsonObject.getString("deskripsi"),
                                jsonObject.getDouble("harga")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter=new AdapterBarang(getApplicationContext(),postList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(RVBarang.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RVBarang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
