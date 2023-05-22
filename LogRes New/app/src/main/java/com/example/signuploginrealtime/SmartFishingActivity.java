package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SmartFishingActivity extends AppCompatActivity {
    TextView backIcon;
    EditText namaProjek, teleponProjek, deskrpisiProjek;
    ImageButton Reset, Kirim;

    FirebaseDatabase database;
    private String Nama;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_smart_fishing);

        namaProjek = findViewById(R.id.NamaProjek);
        teleponProjek = findViewById(R.id.teleponProject);
        deskrpisiProjek = findViewById(R.id.DeskripsiProjek);

        Reset = findViewById(R.id.btnReset);
        Kirim = findViewById(R.id.btnKirim);

        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartFishingActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extra = getIntent().getExtras();
                if(extra != null){
                    Nama = extra.getString("name");
                }
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users/"+Nama+"/Pesanan");

                String nama = namaProjek.getText().toString();
                String telepon = teleponProjek.getText().toString();
                String deskripsi = deskrpisiProjek.getText().toString();

                PesananClass layananClass = new PesananClass(nama, telepon, deskripsi, "Pengajuan_pesanan", "085342248110");
                reference.child(nama).setValue(layananClass);

                Toast.makeText(SmartFishingActivity.this, "Pesanan anda telah terkirim", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SmartFishingActivity.this, DashboardToMonitoringActivity.class));
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaProjek.setText(" ");
                teleponProjek.setText(" ");
                deskrpisiProjek.setText(" ");
            }
        });
    }
}