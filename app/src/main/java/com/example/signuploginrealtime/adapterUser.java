package com.example.signuploginrealtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class adapterUser extends RecyclerView.Adapter<adapterUser.MyViewHolder> {
    private List<ModelUser> mlist;
    private Activity activity;

    public adapterUser(List<ModelUser>mlist,Activity activity){
        this.mlist = mlist;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem=inflater.inflate(R.layout.item_admin_user,parent,false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelUser data=mlist.get(position);
        holder.tv_name.setText(data.getName());
        String user= data.getName();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        database.child(user).child("Pesanan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count_pengajuan = 0;
                int count_proses = 0;
                int count_selesai=0;
                for (DataSnapshot pesanan : dataSnapshot.getChildren()) {
                    String activity = pesanan.child("activity").getValue(String.class);
                    if (activity != null && activity.equals("Pengajuan_pesanan")) {
                        count_pengajuan++;
                    }
                }
                for (DataSnapshot pesanan : dataSnapshot.getChildren()) {
                    String activity = pesanan.child("activity").getValue(String.class);
                    if (activity != null && activity.equals("layanan_belum_tersedia")) {
                        count_proses++;
                    }
                }
                for (DataSnapshot pesanan : dataSnapshot.getChildren()) {
                    String activity = pesanan.child("activity").getValue(String.class);
                    if (activity != null && !activity.equals("Pengajuan_pesanan") && !activity.equals("layanan_belum_tersedia")) {
                        count_selesai++;
                    }
                }
                holder.tv_count_pengajuan.setText(String.valueOf(count_pengajuan));
                holder.tv_count_proses.setText(String.valueOf(count_proses));
                holder.tv_count_selesai.setText(String.valueOf(count_selesai));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Menangani kesalahan pembacaan data
            }
        });
        holder.tv_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Data "+data.getName()+" berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data "+data.getName()+" berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah yakin mau menghapus "+data.getName()+"?");
                // Mengatur tampilan tombol "Iya"
                // Mengatur tampilan tombol "Iya" dan "Tidak" dengan warna yang diinginkan
                AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#1F2E45")); // Mengatur warna tombol "Iya" menjadi merah (#ff0000)

                        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        negativeButton.setTextColor(Color.parseColor("#CE2121")); // Mengatur warna tombol "Tidak" menjadi hijau (#00ff00)
                    }
                });
                alertDialog.show();
            }
        });
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = "com.example.signuploginrealtime.admin_daftar_pesanan";

                // Membuat Intent untuk membuka activity baru
                Intent intent;
                try {
                    Class<?> targetClass = Class.forName(activityName);
                    intent = new Intent(activity, targetClass);
                    intent.putExtra("name",data.getName());
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
        TextView tv_name, tv_count_proses, tv_count_pengajuan,tv_count_selesai;
        CardView card_hasil;
        ImageButton tv_hapus, kotak;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_nama);
            card_hasil=itemView.findViewById(R.id.card_hasil);
            tv_hapus=itemView.findViewById(R.id.tv_hapus);
            kotak=itemView.findViewById(R.id.kotak);
            tv_count_proses=itemView.findViewById(R.id.tv_count_proses);
            tv_count_pengajuan=itemView.findViewById(R.id.tv_count_pengajuan);
            tv_count_selesai=itemView.findViewById(R.id.tv_count_selesai);
        }
    }
}