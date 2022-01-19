package com.example.apk_poloskuy.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apk_poloskuy.DetailBarangActivity;
import com.example.apk_poloskuy.Model.ModelBarang;
import com.example.apk_poloskuy.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.ViewHolder> {
    private final Context context;
    List<ModelBarang> postList;
    public AdapterBarang(Context context, List<ModelBarang> postList) {
        this.postList=postList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlistbarang,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ModelBarang barang = postList.get(position);
        holder.namaprdk.setText(postList.get(position).getNamaPrdk());
        //   holder.descprdk.setText(postList.get(position).getDescPrdk());
        holder.hargaprdk.setText(Rupiah(postList.get(position).getHargaPrdk()));

        String url2 = "https://ws-tif.com/poloskuy/images/db/produk/"+postList.get(position).getImg();
        Glide.with(context)
                .load(url2)
//                .override(320, 210)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgprdk);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailBarangActivity.class );
                intent.putExtra("BARANG", barang);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return postList.size();
    }
    //tes pencarian
    public void FilteredModel(List<ModelBarang> filterModel){
    postList = new ArrayList<>();
    postList.addAll(filterModel);
    notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgprdk;
        TextView namaprdk,stokprdk,hargaprdk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgprdk = itemView.findViewById(R.id.RVimgPrdk);
            namaprdk = itemView.findViewById(R.id.RVnamaPrdk);
            hargaprdk = itemView.findViewById(R.id.RVhargaPrdk);
        }
    }
    private String Rupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
