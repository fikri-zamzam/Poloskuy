package com.example.apk_poloskuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apk_poloskuy.Konek.DbContract;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.example.apk_poloskuy.Konek.VolleyConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button Login;
    EditText Email,Password;
    ProgressDialog progressDialog;

    private RequestQueue rQueue;
    private SharedPrefrencesHelper sharedPrefrencesHelper;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email    = findViewById(R.id.edtLog_email);
        Password = findViewById(R.id.edtLog_password);
        Login    = findViewById(R.id.btnLogin);
        TextView textView = findViewById(R.id.tvToreg);
        progressDialog = new ProgressDialog(LoginActivity.this);
        sharedPrefrencesHelper = new SharedPrefrencesHelper(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAction();
            }
        });

    }

    // fungsi terbaru
    private void loginAction() {
        final String emaill = Email.getText().toString();
        final String pswd = Password.getText().toString();
        if (emaill.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (pswd.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.urlPDO) + "login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("1")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("details");
                                sharedPrefrencesHelper.setId_user(jsonObject1.getString("id_user"));
                                sharedPrefrencesHelper.setEmail(jsonObject1.getString("email"));
                                sharedPrefrencesHelper.setPassword(jsonObject1.getString("password"));
                                sharedPrefrencesHelper.setFullname(jsonObject1.getString("user_fullname"));
                                sharedPrefrencesHelper.setNomorTelfon(jsonObject1.getString("no_telp"));
                                sharedPrefrencesHelper.setGender(jsonObject1.getString("gender"));
                                sharedPrefrencesHelper.setAlamat(jsonObject1.getString("alamat"));
                                sharedPrefrencesHelper.setImguser(jsonObject1.getString("img"));
                                sharedPrefrencesHelper.setStatus("1");

                                Toast.makeText(LoginActivity.this, "Login Successfully! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), NavMainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emaill);
                params.put("password", pswd);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}