package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomActivity extends AppCompatActivity {

    TextView backIcon;
    EditText namaProjek, teleponProjek, deskrpisiProjek;
    ImageButton Reset, Kirim;
    private String nama, email, notelp, pass, fotoprofile;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


        namaProjek = findViewById(R.id.NamaProjek);
        teleponProjek = findViewById(R.id.teleponProject);
        deskrpisiProjek = findViewById(R.id.DeskripsiProjek);

        Reset = findViewById(R.id.btnReset);
        Kirim = findViewById(R.id.btnKirim);

        backIcon = findViewById(R.id.textView22);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nama = extra.getString("name");
            email = extra.getString("email");
            notelp = extra.getString("notelp");
            pass = extra.getString("password");
            fotoprofile = extra.getString("Foto");
        }

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaProjek.setText(" ");
                teleponProjek.setText(" ");
                deskrpisiProjek.setText(" ");
            }
        });

        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users/"+nama+"/Pesanan");

                String ProjekName = namaProjek.getText().toString();
                String ProjekTelepon = teleponProjek.getText().toString();
                String deskripsi = deskrpisiProjek.getText().toString();

                reference = database.getReference("users/"+nama+"/Pesanan");

                PesananClass layananClass = new PesananClass(ProjekName, ProjekTelepon, deskripsi, "Pengajuan_pesanan", "-","-","Custom");
                reference.child(ProjekName).setValue(layananClass);

                Toast.makeText(CustomActivity.this, "Pesanan anda telah terkirim", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomActivity.this, DashboardToMonitoringActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}