package com.example.apk_poloskuy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Adapter.AdapterBarang;
import com.example.apk_poloskuy.MainActivitySetting;
import com.example.apk_poloskuy.Model.ModelBarang;
import com.example.apk_poloskuy.NavMainActivity;
import com.example.apk_poloskuy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RVBarang extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    List<ModelBarang> postList=new ArrayList<>();
    String url="https://ws-tif.com/poloskuy/system/API/listProduk.php";
    AdapterBarang adapter;
    List<ModelBarang> filterList=new ArrayList<>();
    RecyclerView recyclerView;
    EditText search;
    BottomNavigationView btnBawahRV;
    SearchView searchView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_barang);

        btnBawahRV =findViewById(R.id.menu_bawahRV);
        btnBawahRV.setOnNavigationItemSelectedListener(this);
        searchView=findViewById(R.id.search_item);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        GetData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText =newText.toLowerCase();
                List<ModelBarang> modelBarangs = new ArrayList<>();
                for (ModelBarang mode: postList ){
                    String  nama = mode.getNamaPrdk().toLowerCase();
                    if (nama.contains(newText)){
                        modelBarangs.add(mode);
                    }
                }
                adapter.FilteredModel(modelBarangs);
                return true;
            }
        });

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
                                jsonObject.getString("id_produk"),
                                jsonObject.getString("img"),
                                jsonObject.getString("nama_produk"),
                                jsonObject.getString("deskripsi"),
                                jsonObject.getString("stok"),
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

//navigasi
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(RVBarang.this, NavMainActivity.class);
                startActivity(intent);
                break;
            case R.id.all_list:
                Intent inte=new Intent(RVBarang.this, RVBarang.class);
                startActivity(inte);
                break;
            case R.id.my_setting:
                Intent ente=new Intent(RVBarang.this,MainActivitySetting.class);
                startActivity(ente);
                break;
            case R.id.pesananku:
                Intent mPesanan = new Intent(RVBarang.this,RVStatusPengiriman.class);
                startActivity(mPesanan);
                break;
        }

        return true;
    }


    private void filter(String newText){
        List<ModelBarang> filteredList =new ArrayList<>();
        for (ModelBarang model:postList){
            if (model.getNamaPrdk().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(model);
            }
        }
        adapter.FilteredModel(filteredList);
   }
}
