package com.example.signuploginrealtime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IsiNotifikasiActivity_backup extends AppCompatActivity {

    TextView backicon, kotaklayanan ;
    TextView  txtnama;
    TextView  txtnoproyek;
    TextView  txtdeskripsi;
    TextView txtnamadev;
    TextView  txtnodev;
    private String nameProjek,activity, jenisLayanan,namaDev,nomorDev,deskripsiProjek,teleponProjek;


    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FUllScreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_isi_notifikasi_backup);

//        DATA untuk tampilkan isi notifiikasi
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nameProjek = extra.getString("nameProjek");
            activity = extra.getString("activity");
            jenisLayanan = extra.getString("jenisLayanan");
            teleponProjek = extra.getString("teleponProjek");
            deskripsiProjek = extra.getString("deskripsiProjek");
            nomorDev = extra.getString("nomorDev");
            namaDev = extra.getString("namaDev");
        }

        kotaklayanan.findViewById(R.id.kotaklayanan);
        txtnama.findViewById(R.id.txtnama);
        txtnoproyek.findViewById(R.id.txtnoproyek);
        txtdeskripsi.findViewById(R.id.txtdeskripsi);
        txtnamadev.findViewById(R.id.txtnamadev);
        txtnodev.findViewById(R.id.txtnodev);

        kotaklayanan.setText(jenisLayanan);
        txtnama.setText(nameProjek);
        txtnoproyek.setText(teleponProjek);
        txtdeskripsi.setText(deskripsiProjek);
        txtnamadev.setText(namaDev);
        txtnodev.setText(nomorDev);

        backicon=findViewById(R.id.backIcon);
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
