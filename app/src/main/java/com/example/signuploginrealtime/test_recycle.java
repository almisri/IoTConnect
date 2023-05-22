package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class test_recycle extends AppCompatActivity {
    adapter_layanan adapter_layanan;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("users/Ahmad Ramli");
    ArrayList<ModelLayanan>listLayanan;
    RecyclerView tv_tampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycle);
        tv_tampil=findViewById(R.id.tv_tampil);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mlayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void tampilData() {
        database.child("Pesanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLayanan =new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()){
                    ModelLayanan layanan=item.getValue((ModelLayanan.class));
                    layanan.setKey(item.getKey());
                    listLayanan.add(layanan);
                }
                adapter_layanan = new adapter_layanan(listLayanan, test_recycle.this );
                tv_tampil.setAdapter(adapter_layanan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}