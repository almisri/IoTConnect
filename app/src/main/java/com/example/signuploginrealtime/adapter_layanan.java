package com.example.signuploginrealtime;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

public class adapter_layanan extends RecyclerView.Adapter<adapter_layanan.MyViewHolder> {
    private List<ModelLayanan> mlist;
    private Activity activity;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference();

    public adapter_layanan(List<ModelLayanan>mlist,Activity activity){
        this.mlist = mlist;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem=inflater.inflate(R.layout.layout_item,parent,false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final ModelLayanan data=mlist.get(position);
    holder.tv_name.setText(data.getName());
    String activitykotak=data.getActivity();
    if (activitykotak.equals("Pengajuan_pesanan")){
        holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring);
    } else if (activitykotak.equals(("layanan_belum_tersedia"))) {
        holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring_yellow);
    }else{
        holder.kotak.setBackgroundResource(R.drawable.containerdashboardmonitoring_blue);
        holder.run_activity.setBackgroundResource(R.drawable.righticon_white);
        holder.tv_name.setTextColor(Color.parseColor("#FFFFFF"));
    }

        holder.run_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = "com.example.signuploginrealtime."+data.getActivity();

                // Membuat Intent untuk membuka activity baru
                Intent intent;
                try {
                    Class<?> targetClass = Class.forName(activityName);
                    intent = new Intent(activity, targetClass);
                    activity.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = "com.example.signuploginrealtime."+data.getActivity();

                // Membuat Intent untuk membuka activity baru
                Intent intent;
                try {
                    Class<?> targetClass = Class.forName(activityName);
                    intent = new Intent(activity, targetClass);
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
        TextView tv_name;
        CardView card_hasil;
        ImageButton run_activity, kotak;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_nama);
            card_hasil=itemView.findViewById(R.id.card_hasil);
            run_activity=itemView.findViewById(R.id.run_activity);
            kotak=itemView.findViewById(R.id.kotak);
        }
    }
}
