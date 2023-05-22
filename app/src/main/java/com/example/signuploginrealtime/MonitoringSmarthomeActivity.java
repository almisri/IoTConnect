package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MonitoringSmarthomeActivity extends AppCompatActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tempref = database.getReference("users/Arsan Akbar/Layanan/Smart Home/temp_ruangan");
    DatabaseReference nama_user = database.getReference("users/Arsan Akbar/name");
    Calendar calendar = Calendar.getInstance();
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    ImageButton btn_ruangtamu,btn_kamarmandi, btn_dapur,btn_arrow;
    ImageView iconcuaca;
    TextView text_temp, text_profile, text_bagian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_smarthome);

        btn_ruangtamu=findViewById(R.id.button_ruang_tamu);
        btn_kamarmandi=findViewById(R.id.button_kamar_mandi);
        btn_dapur=findViewById(R.id.button_dapur);
        text_temp=findViewById(R.id.text_temp);
        text_profile=findViewById(R.id.text_profile);
        text_bagian=findViewById(R.id.text_bagian);
        iconcuaca=findViewById(R.id.iconcuaca);
        btn_arrow=findViewById(R.id.arrow_button);
        btn_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_ruangtamu.setBackgroundResource(R.drawable.blue_ruang_tamu);
        text_bagian.setText("Bagian ruang tamu");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.container, new fragment_ruang_tamu());
        ft.commit();


        if (hourOfDay >= 6 && hourOfDay < 19) {
            iconcuaca.setImageResource(R.drawable.cuaca_hari_cerah);
        } else {
            iconcuaca.setImageResource(R.drawable.cuaca_malam);
        }

        tempref.addValueEventListener(new ValueEventListener() {                                // AMBIL DATA TEMP RUANGAN
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp= snapshot.getValue(String.class);
                text_temp.setText(temp+"°C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });                                                                                         //AMBIL DATA USER
        nama_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nama= snapshot.getValue(String.class);
                text_profile.setText("Selamat Datang, "+nama);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btn_ruangtamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ruangtamu.setBackgroundResource(R.drawable.blue_ruang_tamu);
                btn_kamarmandi.setBackgroundResource(R.drawable.white_kamar_mandi);
                btn_dapur.setBackgroundResource(R.drawable.white_dapur);
                text_bagian.setText("Bagian ruang tamu");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_ruang_tamu());
                ft.commit();
            }
        });
        btn_kamarmandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ruangtamu.setBackgroundResource(R.drawable.white_ruang_tamu);
                btn_kamarmandi.setBackgroundResource(R.drawable.blue_kamar_mandi);
                btn_dapur.setBackgroundResource(R.drawable.white_dapur);
                text_bagian.setText("Bagian kamar mandi");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_ruang_tamu());
                ft.commit();
            }
        });
        btn_dapur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ruangtamu.setBackgroundResource(R.drawable.white_ruang_tamu);
                btn_kamarmandi.setBackgroundResource(R.drawable.white_kamar_mandi);
                btn_dapur.setBackgroundResource(R.drawable.blue_dapur);
                text_bagian.setText("Bagian dapur");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_ruang_tamu());
                ft.commit();
            }
        });
    }
}