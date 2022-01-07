package com.example.apk_poloskuy.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class ModelBarang implements Parcelable {

    String idPrdk,img,namaPrdk,descPrdk;
    Double hargaPrdk;
    public ModelBarang(String id,String gambar, String nama, String deskripsi,Double harga) {
        idPrdk = id;
        img = gambar;
        namaPrdk = nama;
        descPrdk = deskripsi;
        hargaPrdk = harga;
    }

    public String getIdPrdk() {return idPrdk;}

    public void setIdPrdk(String idPrdk) {this.idPrdk = idPrdk;}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNamaPrdk() {
        return namaPrdk;
    }

    public void setNamaPrdk(String namaPrdk) {
        this.namaPrdk = namaPrdk;
    }

    public String getDescPrdk() {
        return descPrdk;
    }

    public void setDescPrdk(String descPrdk) {
        this.descPrdk = descPrdk;
    }

    public Double getHargaPrdk() {
        return hargaPrdk;
    }

    public void setHargaPrdk(Double hargaPrdk) {
        this.hargaPrdk = hargaPrdk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPrdk);
        dest.writeString(img);
        dest.writeString(namaPrdk);
        dest.writeString(descPrdk);
        dest.writeDouble(hargaPrdk);
    }

    public ModelBarang(Parcel in) {
        idPrdk = in.readString();
        img = in.readString();
        namaPrdk = in.readString();
        descPrdk = in.readString();
        hargaPrdk = in.readDouble();
    }

    public static final Creator<ModelBarang> CREATOR = new Creator<ModelBarang>() {
        @Override
        public ModelBarang createFromParcel(Parcel in) {
            return new ModelBarang(in);
        }

        @Override
        public ModelBarang[] newArray(int size) {
            return new ModelBarang[size];
        }
    };
}