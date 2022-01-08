package com.example.apk_poloskuy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apk_poloskuy.Activity.BarangDiterima;
import com.example.apk_poloskuy.DashboardActivity;
import com.example.apk_poloskuy.DetailBarangActivity;
import com.example.apk_poloskuy.KonfirmasiPesananActivity;
import com.example.apk_poloskuy.Model.ModelStatusKirim;
import com.example.apk_poloskuy.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterStatusKirim extends RecyclerView.Adapter<AdapterStatusKirim.ViewHolder> {
    private final Context context;
    List<ModelStatusKirim> postStatus;

    public AdapterStatusKirim(Context context, List<ModelStatusKirim> postStatus) {
        this.postStatus = postStatus;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempesananku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ModelStatusKirim status = postStatus.get(position);

        holder.namaBrgstat.setText(postStatus.get(position).getNamaBrgStat());
        holder.statuss.setText(postStatus.get(position).getStatusBrg());
        holder.hargaBrgstat.setText(Rupiah(postStatus.get(position).getHargaBrgStat()));
        holder.Qtt.setText(postStatus.get(position).getQtt());

        String url2 = "https://ws-tif.com/poloskuy/images/db/produk/"+postStatus.get(position).getImgBrgStat();
        Glide.with(context)
                .load(url2)
                .override(150, 150)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgBrgstat);

        holder.sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), KonfirmasiPesananActivity.class );
                intent.putExtra("STATUS", status);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBrgstat;
        TextView namaBrgstat, statuss, hargaBrgstat,Qtt;
        Button sampai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBrgstat = itemView.findViewById(R.id.RVimgPesanan);
            namaBrgstat = itemView.findViewById(R.id.RVnamaPesanan);
            statuss = itemView.findViewById(R.id.RV_StatusPesanan);
            hargaBrgstat = itemView.findViewById(R.id.RVhargaPesanan);
            namaBrgstat = itemView.findViewById(R.id.RVnamaPesanan);
            Qtt = itemView.findViewById(R.id.RVjmlPesanan);
            sampai = itemView.findViewById(R.id.btn_sampai);

        }
    }
    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}