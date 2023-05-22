package com.example.signuploginrealtime;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.MyViewHolder> {
    @NonNull
    private List<ModelNotifikasi> mlist;
    private Activity activity;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference();

    public AdapterNotifikasi(List<ModelNotifikasi>mlist,Activity activity){
        this.mlist = mlist;
        this.activity=activity;

    }
    public AdapterNotifikasi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem=inflater.inflate(R.layout.item_notifikasi,parent,false);
        return new MyViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotifikasi.MyViewHolder holder, int position) {
        final ModelNotifikasi data=mlist.get(position);
        holder.tv_nama.setText(data.getName());
        String activitykotak=data.getActivity();
        if (activitykotak.equals("Pengajuan_pesanan")){
            holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring);
        } else if (activitykotak.equals(("layanan_belum_tersedia"))) {
            holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring_yellow);
        }else{
            holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring_blue);
            holder.run_activity.setBackgroundResource(R.drawable.righticon_white);
            holder.tv_nama.setTextColor(Color.parseColor("#FFFFFF"));
        }
        holder.run_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = "com.example.signuploginrealtime.IsiNotifikasiActivity";

                // Membuat Intent untuk membuka activity baru
                Intent intent;

                try {
                    Class<?> targetClass = Class.forName(activityName);
                    intent = new Intent(activity, targetClass);

                    //Data Projek
                    intent.putExtra("nameProjek", data.getName());
                    intent.putExtra("activity", data.getActivity());
                    intent.putExtra("jenisLayanan", data.getJenisLayanan());
                    intent.putExtra("teleponProjek", data.getTeleponprojek());
                    intent.putExtra("deskripsiProjek", data.getDeskripsiprojek());
                    intent.putExtra("nomorDev", data.getNomorDev());
                    intent.putExtra("namaDev", data.getNamaDev());

                    activity.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = "com.example.signuploginrealtime.IsiNotifikasiActivity";

                // Membuat Intent untuk membuka activity baru
                Intent intent;

                try {
                    Class<?> targetClass = Class.forName(activityName);
                    intent = new Intent(activity, targetClass);

//                    Data Projek
                    intent.putExtra("nameProjek", data.getName());
                    intent.putExtra("activity", data.getActivity());
                    intent.putExtra("jenisLayanan", data.getJenisLayanan());
                    intent.putExtra("teleponProjek", data.getTeleponprojek());
                    intent.putExtra("deskripsiProjek", data.getDeskripsiprojek());
                    intent.putExtra("nomorDev", data.getNomorDev());
                    intent.putExtra("namaDev", data.getNamaDev());

                    activity.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        CardView card_hasil;
        ImageButton run_activity, kotak;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama=itemView.findViewById(R.id.tv_nama);
            card_hasil=itemView.findViewById(R.id.card_hasil);
            run_activity=itemView.findViewById(R.id.run_activity);
            kotak=itemView.findViewById(R.id.kotak);

        }
    }
}
