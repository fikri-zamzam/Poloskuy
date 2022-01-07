package com.example.apk_poloskuy.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelStatusKirim implements Parcelable{
    String NamaBrgStat,imgBrgStat,statusBrg,idTrans,IdDetTrans,Qtt;
    Double hargaBrgStat;

    public ModelStatusKirim(String namaBrgStat, String imgBrgStat, String statusBrg, String idTrans, String idDetTrans, String qtt, Double hargaBrgStat) {
        NamaBrgStat = namaBrgStat;
        this.imgBrgStat = imgBrgStat;
        this.statusBrg = statusBrg;
        this.idTrans = idTrans;
        IdDetTrans = idDetTrans;
        Qtt = qtt;
        this.hargaBrgStat = hargaBrgStat;
    }


    public String getNamaBrgStat() {
        return NamaBrgStat;
    }

    public void setNamaBrgStat(String namaBrgStat) {
        NamaBrgStat = namaBrgStat;
    }

    public String getImgBrgStat() {
        return imgBrgStat;
    }

    public void setImgBrgStat(String imgBrgStat) {
        this.imgBrgStat = imgBrgStat;
    }

    public String getStatusBrg() {
        return statusBrg;
    }

    public void setStatusBrg(String statusBrg) {
        this.statusBrg = statusBrg;
    }

    public String getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(String idTrans) {
        this.idTrans = idTrans;
    }

    public String getIdDetTrans() {
        return IdDetTrans;
    }

    public void setIdDetTrans(String idDetTrans) {
        IdDetTrans = idDetTrans;
    }

    public String getQtt() {
        return Qtt;
    }

    public void setQtt(String qtt) {
        Qtt = qtt;
    }

    public Double getHargaBrgStat() {
        return hargaBrgStat;
    }

    public void setHargaBrgStat(Double hargaBrgStat) {
        this.hargaBrgStat = hargaBrgStat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NamaBrgStat);
        dest.writeString(imgBrgStat);
        dest.writeString(statusBrg);
        dest.writeString(idTrans);
        dest.writeString(IdDetTrans);
        dest.writeString(Qtt);
        if (hargaBrgStat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(hargaBrgStat);
        }
    }

    protected ModelStatusKirim(Parcel in) {
        NamaBrgStat = in.readString();
        imgBrgStat = in.readString();
        statusBrg = in.readString();
        idTrans = in.readString();
        IdDetTrans = in.readString();
        Qtt = in.readString();
        if (in.readByte() == 0) {
            hargaBrgStat = null;
        } else {
            hargaBrgStat = in.readDouble();
        }
    }


    public static final Creator<ModelStatusKirim> CREATOR = new Creator<ModelStatusKirim>() {
        @Override
        public ModelStatusKirim createFromParcel(Parcel in) {
            return new ModelStatusKirim(in);
        }

        @Override
        public ModelStatusKirim[] newArray(int size) {
            return new ModelStatusKirim[size];
        }
    };
}
