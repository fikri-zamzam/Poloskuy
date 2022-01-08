package com.example.apk_poloskuy.Konek;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPrefrencesHelper {
    private SharedPreferences sharedPreferences;
    private Context context;
    private String id_user= "id_user", email = "email",password = "password",fullname = "fullname",
                   noTelp = "noTelp",gender = "jk", alamat = "alamat", status = "status", img="img";
    public SharedPrefrencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("login_session",
                Context.MODE_PRIVATE);
        this.context = context;
    }
    //getter
    public String getId_user() {
        return sharedPreferences.getString(id_user, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(password, "");
    }

    public String getFullname() {
        return sharedPreferences.getString(fullname, "");
    }

    public String getNomorTelfon() {
        return sharedPreferences.getString(noTelp, "");
    }

    public String getGender() {
        return sharedPreferences.getString(gender, "");
    }

    public String getAlamat() {
        return sharedPreferences.getString(alamat, "");
    }

    public String getStatus() {
        return sharedPreferences.getString(status, "0");
    }

    public String getImguser() {
        return sharedPreferences.getString(img, "0");
    }

    //setter

    public void setId_user(String id_user) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.id_user, id_user);
        edit.commit();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.email, email);
        edit.commit();
    }

    public void setPassword(String password) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.password, password);
        edit.commit();
    }

    public void setFullname(String fullname) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.fullname, fullname);
        edit.commit();
    }

    public void setNomorTelfon(String noTelp) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.noTelp, noTelp);
        edit.commit();
    }

    public void setGender(String gender) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (gender.equals("L")) {
            gender = "Laki-laki";
        } else {
            gender = "Perempuan";
        }
        edit.putString(this.gender, gender);
        edit.commit();
    }

    public void setAlamat(String alamat) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.alamat, alamat);
        edit.commit();
    }

    public void setStatus(String status) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.status, status);
        edit.commit();
    }

    public void setImguser(String img) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.img, img);
        edit.commit();
    }
}