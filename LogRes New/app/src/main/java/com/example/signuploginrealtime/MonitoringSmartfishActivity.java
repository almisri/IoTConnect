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

public class MonitoringSmartfishActivity extends AppCompatActivity{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tempref = database.getReference("users/Risvan/Layanan/Smart Fish/temp_ruangan");
    DatabaseReference nama_user = database.getReference("users/Risvan/name");
    Calendar calendar = Calendar.getInstance();
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    ImageButton btn_pengairan,btn_ikan, btn_pakan,btn_arrow;
    ImageView iconcuaca;
    TextView text_temp, text_profile, text_bagian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_smartfish);

        btn_pengairan=findViewById(R.id.button_pengairan);
        btn_ikan=findViewById(R.id.button_ikan);
        btn_pakan=findViewById(R.id.button_pakan);
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

        btn_pengairan.setBackgroundResource(R.drawable.blue_pengairan);
        text_bagian.setText("Bagian pengairan");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.container, new fragment_pengairan());
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


        btn_pengairan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pengairan.setBackgroundResource(R.drawable.blue_pengairan);
                btn_ikan.setBackgroundResource(R.drawable.white_ikan);
                btn_pakan.setBackgroundResource(R.drawable.white_pakan);
                text_bagian.setText("Bagian pengairan");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_pengairan());
                ft.commit();
            }
        });
        btn_ikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pengairan.setBackgroundResource(R.drawable.white_pengairan);
                btn_ikan.setBackgroundResource(R.drawable.blue_ikan);
                btn_pakan.setBackgroundResource(R.drawable.white_pakan);
                text_bagian.setText("Bagian ikan");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_pengairan());
                ft.commit();
            }
        });
        btn_pakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pengairan.setBackgroundResource(R.drawable.white_pengairan);
                btn_ikan.setBackgroundResource(R.drawable.white_ikan);
                btn_pakan.setBackgroundResource(R.drawable.blue_pakan);
                text_bagian.setText("Bagian pakan");
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.container, new fragment_pengairan());
                ft.commit();
            }
        });

    }
}