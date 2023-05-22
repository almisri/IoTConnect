package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardToMonitoringActivity extends AppCompatActivity {
    adapter_layanan adapter_layanan;
    ArrayList<ModelLayanan> listLayanan;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("users");
    RecyclerView tv_tampil;
    TextView HomeIcon, ProfileIcon;
    TextView namaAkun;
    private String nama, email, notelp, pass, fotoprofile;
    TextView containersmarthome, containersmartfish, containersmartfarm, containercustom;
    TextView txtsmarthome, txtsmartfish, txtsmartfarm, txtcustom;
    TextView smarthomeright, smartfishright, smartfarmright, customright;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_to_monitoring);

        namaAkun = findViewById(R.id.namaAkun);
        txtsmarthome = findViewById(R.id.txtsmarthome);

        HomeIcon = findViewById(R.id.homeicon);
        ProfileIcon = findViewById(R.id.profileicon);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nama = extra.getString("name");
            email = extra.getString("email");
            notelp = extra.getString("notelp");
            pass = extra.getString("password");
            fotoprofile = extra.getString("Foto");
        }

        HomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardToMonitoringActivity.this, DashboardActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardToMonitoringActivity.this, ProfileActivity.class);
                Bundle extra = getIntent().getExtras();
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

        //TAMPILKAN DATA CARDVIEW!!
        tv_tampil=findViewById(R.id.tv_tampil);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mlayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();

        showAllUserData();

    }

    private void tampilData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");

        database.child(nameUser).child("Pesanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Child "Pesanan" ada dalam database
                    listLayanan = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        ModelLayanan layanan = item.getValue(ModelLayanan.class);
                        layanan.setKey(item.getKey());
                        listLayanan.add(layanan);
                    }
                    adapter_layanan = new adapter_layanan(listLayanan, DashboardToMonitoringActivity.this);
                    tv_tampil.setAdapter(adapter_layanan);
                } else {
                    // Child "Pesanan" tidak ada dalam database
                    Intent belumMemilikiIntent = new Intent(DashboardToMonitoringActivity.this, BlmPesanActivity.class);
                    onBackPressed();
                    startActivity(belumMemilikiIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent intent = getIntent();
                String nameUser = intent.getStringExtra("name");
                namaAkun.setText("Halo "+nameUser);
            }
        });
    }
    private void showAllUserData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        namaAkun.setText("Halo "+nameUser);
    }
}