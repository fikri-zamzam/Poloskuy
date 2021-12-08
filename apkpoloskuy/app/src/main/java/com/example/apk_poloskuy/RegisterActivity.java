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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.apk_poloskuy.Konek.DbContract;
import com.example.apk_poloskuy.Konek.VolleyConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView textView;
    EditText email,password,fullname,noTelp,gender,alamat;
    Button register;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textView = (TextView) findViewById(R.id.tvTolog);
        email = (EditText) findViewById(R.id.edtReg_email);
        password = (EditText) findViewById(R.id.edtReg_password);
        fullname = (EditText) findViewById(R.id.edtReg_fullname);
        noTelp = (EditText) findViewById(R.id.edtReg_noTelp);
        gender = (EditText) findViewById(R.id.edtReg_gender);
        alamat = (EditText) findViewById(R.id.edtReg_alamat);
        register = (Button)findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class );
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPassword = password.getText().toString();
                String sFullname = fullname.getText().toString();
                String sNoTelp   = noTelp.getText().toString();
                String sGender   = gender.getText().toString();
                String sAlamat   = alamat.getText().toString();


                CreateDataToServer(sEmail, sPassword,sFullname,sNoTelp,sGender,sAlamat);
                Intent registerIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(registerIntent);

            }
        });



    }

    public void CreateDataToServer( final String email, final String password,final String fullname,
                                    final String noTelp,final String gender,final String alamat) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("fullname", fullname);
                    params.put("noTelp", noTelp);
                    params.put("jk", gender);
                    params.put("alamat", alamat);
                    return params;
                }
            };

            VolleyConnection.getInstance(RegisterActivity.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }
    }

    /*Fungsi untuk mengecek ketersedian jaringan internet*/
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}