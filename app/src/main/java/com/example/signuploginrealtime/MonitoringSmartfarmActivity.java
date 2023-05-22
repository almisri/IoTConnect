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

public class MonitoringSmartfarmActivity extends AppCompatActivity{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tempref = database.getReference("users/Al Misri H/Layanan/Smart Farm/temp_ruangan");
    DatabaseReference nama_user = database.getReference("users/Al Misri H/name");
    Calendar calendar = Calendar.getInstance();
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    ImageButton btn_bayam,btn_selada, btn_wortel,btn_arrow;
    ImageView iconcuaca;
    TextView text_temp, text_profile, text_bagian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_smartfarm);

        btn_bayam=findViewById(R.id.button_bayam);
        btn_selada=findViewById(R.id.button_selada);
        btn_wortel=findViewById(R.id.button_wortel);
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

        btn_bayam.setBackgroundResource(R.drawable.blue_bayam);
        text_bagian.setText("Bagian bayam");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.container, new fragment_bayam());
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


        btn_bayam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bayam.setBackgroundResource(R.drawable.blue_bayam);
                btn_selada.setBackgroundResource(R.drawable.white_selada);
                btn_wortel.setBackgroundResource(R.drawable.white_wortel);
                text_bagian.setText("Bagian bayam");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_bayam());
                ft.commit();
            }
        });
        btn_selada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bayam.setBackgroundResource(R.drawable.white_bayam);
                btn_selada.setBackgroundResource(R.drawable.blue_selada);
                btn_wortel.setBackgroundResource(R.drawable.white_wortel);
                text_bagian.setText("Bagian selada");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_bayam());
                ft.commit();
            }
        });
        btn_wortel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bayam.setBackgroundResource(R.drawable.white_bayam);
                btn_selada.setBackgroundResource(R.drawable.white_selada);
                btn_wortel.setBackgroundResource(R.drawable.blue_wortel);
                text_bagian.setText("Bagian wortel");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_bayam());
                ft.commit();
            }
        });
    }
}