package com.example.signuploginrealtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter_Daftar_pesanan_admin extends RecyclerView.Adapter<Adapter_Daftar_pesanan_admin.MyViewHolder> {
    private List<ModelNotifikasi> mlist;
    private AppCompatActivity activity;
    private String name;

    public Adapter_Daftar_pesanan_admin(List<ModelNotifikasi>mlist,AppCompatActivity  activity, String name ){
        this.mlist = mlist;
        this.activity=activity;
        this.name=name;
    }

    @NonNull
    @Override
    public Adapter_Daftar_pesanan_admin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem=inflater.inflate(R.layout.item_daftar_pesanan_admin,parent,false);
        return new Adapter_Daftar_pesanan_admin.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Daftar_pesanan_admin.MyViewHolder holder, int position) {
        final ModelNotifikasi data=mlist.get(position);
        holder.tv_name.setText("Username : "+name);
        holder.tv_nama_projek.setText("Pesanan     :" +data.getName());
        holder.tv_noTelp.setText("No. Tel         : "+data.getTeleponprojek());
        int nopesanan = position + 1;
        holder.tv_jenis.setText("Pesanan "+nopesanan+" - "+data.getJenisLayanan());
        String activityuser= data.getActivity();
        if (activityuser.equals("Pengajuan_pesanan")){
            holder.tv_proses.setVisibility(View.VISIBLE);
            holder.tv_selesai.setVisibility(View.VISIBLE);
        }else if (activityuser.equals("layanan_belum_tersedia")){
            holder.tv_proses.setVisibility(View.INVISIBLE);
            holder.tv_selesai.setVisibility(View.VISIBLE);
        }else {
            holder.tv_proses.setVisibility(View.GONE);
            holder.tv_selesai.setVisibility(View.GONE);
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users/"+name+"/Pesanan");
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
        holder.tv_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_proses proses=new fragment_proses();
                Bundle bundle= new Bundle();
                bundle.putString("key_data", data.getKey());
                bundle.putString("nama", name);
                proses.setArguments(bundle);
                // Mengganti Fragment saat tombol diklik
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, proses);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        holder.tv_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_terima terima=new fragment_terima();
                Bundle bundle= new Bundle();
                bundle.putString("key_data", data.getKey());
                bundle.putString("nama", name);
                terima.setArguments(bundle);
                // Mengganti Fragment saat tombol diklik
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, terima);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_jenis, tv_nama_projek,tv_noTelp;
        CardView card_hasil;
        ImageButton tv_hapus, kotak,tv_proses,tv_selesai;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_nama);
            card_hasil=itemView.findViewById(R.id.card_hasil);
            tv_hapus=itemView.findViewById(R.id.tv_hapus);
            kotak=itemView.findViewById(R.id.kotak);
            tv_jenis=itemView.findViewById(R.id.tv_jenis);
            tv_nama_projek=itemView.findViewById(R.id.tv_nama_projek);
            tv_noTelp=itemView.findViewById(R.id.tv_noTelp);
            tv_proses=itemView.findViewById(R.id.tv_proses);
            tv_selesai=itemView.findViewById(R.id.tv_selesai);
        }
    }
}