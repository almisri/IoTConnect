package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IsiNotifikasiActivity extends AppCompatActivity {
    TextView backicon, kotaklayanan ,text1;
    TextView  txtnama;
    ImageView check;
    TextView  txtnoproyek;
    TextView  txtdeskripsi;
    TextView txtnamadev;
    TextView  txtnodev;


    private String nameProjek,activity, jenisLayanan,namaDev,nomorDev,deskripsiProjek,teleponProjek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_notifikasi);

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

        kotaklayanan=findViewById(R.id.kotaklayanan);
        text1=findViewById(R.id.text1);
        txtnama=findViewById(R.id.txtnama);
        txtnoproyek=findViewById(R.id.txtnoproyek);
        txtdeskripsi=findViewById(R.id.txtdeskripsi);
        txtnamadev=findViewById(R.id.txtnamadev);
        txtnodev=findViewById(R.id.txtnodev);
        check=findViewById(R.id.check);

        if (activity.equals("Pengajuan_pesanan")){
            text1.setText("Pesanan anda masih pengajuan");
            check.setVisibility(View.GONE);
        }else if(activity.equals("layanan_belum_tersedia")){
            text1.setText("Pesanan anda sudah diterima");
            check.setVisibility(View.VISIBLE);
        }else{
            text1.setText("Pesanan anda telah selesai");
            check.setVisibility(View.VISIBLE);
        }

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