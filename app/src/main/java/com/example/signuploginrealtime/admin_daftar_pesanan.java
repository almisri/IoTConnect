package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class admin_daftar_pesanan extends AppCompatActivity {
    Adapter_Daftar_pesanan_admin adapter_daftar_pesanan_admin;
    ArrayList<ModelNotifikasi> listDaftar;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("users");
    RecyclerView tv_tampil;
    ImageButton backicon;
    TextView txtnotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_daftar_pesanan);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        txtnotif=findViewById(R.id.txtNotif);
        backicon=findViewById(R.id.arrow_button);
//        txtnotif.setText(name);
        tv_tampil=findViewById(R.id.tv_tampil);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mlayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SIMPAN DATA LOGIN USERNAME KE INTERNAL
                onBackPressed();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

    }
    private void tampilData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        database.child(name).child("Pesanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Child "Pesanan" ada dalam database
                    listDaftar = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        ModelNotifikasi daftar = item.getValue(ModelNotifikasi.class);
                        daftar.setKey(item.getKey());
                        listDaftar.add(daftar);
                    }
                    adapter_daftar_pesanan_admin = new Adapter_Daftar_pesanan_admin(listDaftar, admin_daftar_pesanan.this,name);
                    tv_tampil.setAdapter(adapter_daftar_pesanan_admin);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}