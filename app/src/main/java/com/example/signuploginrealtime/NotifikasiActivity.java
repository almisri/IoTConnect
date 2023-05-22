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

import java.util.ArrayList;

public class NotifikasiActivity extends AppCompatActivity {
    AdapterNotifikasi AdapterNotifikasi;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("users");
    ArrayList<ModelNotifikasi> listNotifikasi;
    RecyclerView tv_tampil;
    ImageButton arrowButton;
    TextView HomeIcon, MonitoringIcon, ProfileIcon;
    private String nama, email, notelp, pass, fotoprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nama = extra.getString("name");
            email = extra.getString("email");
            notelp = extra.getString("notelp");
            pass = extra.getString("password");
            fotoprofile = extra.getString("Foto");
        }

        HomeIcon = findViewById(R.id.homeicon);
        MonitoringIcon = findViewById(R.id.monitoringicon);
        ProfileIcon = findViewById(R.id.profileicon);
        arrowButton=findViewById(R.id.arrow_button);
        tv_tampil=findViewById(R.id.tv_tampilnotif);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mlayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifikasiActivity.this, ProfileActivity.class);
                Bundle extra = getIntent().getExtras();
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });


        HomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifikasiActivity.this, DashboardActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        MonitoringIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifikasiActivity.this, DashboardToMonitoringActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

    }

    private void tampilData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");

        database.child(nameUser).child("Pesanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Child "Pesanan" ada dalam database
                    listNotifikasi = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        ModelNotifikasi notifikasi = item.getValue(ModelNotifikasi.class);
                        notifikasi.setKey(item.getKey());
                        listNotifikasi.add(notifikasi);
                    }
                    AdapterNotifikasi = new AdapterNotifikasi(listNotifikasi, NotifikasiActivity.this);
                    tv_tampil.setAdapter(AdapterNotifikasi);
                } else {
                    // Child "Pesanan" tidak ada dalam database
                    Intent belumMemilikiIntent = new Intent(NotifikasiActivity.this, BlmPesanActivity.class);
                    onBackPressed();
                    startActivity(belumMemilikiIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}