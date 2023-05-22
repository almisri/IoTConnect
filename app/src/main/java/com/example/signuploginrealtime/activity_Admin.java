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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class activity_Admin extends AppCompatActivity {
    adapterUser adapterUser;
    ArrayList<ModelUser> listUser;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("users");
    RecyclerView tv_tampil;
    ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout=findViewById(R.id.logout);
        tv_tampil=findViewById(R.id.tv_tampil);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mlayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SIMPAN DATA LOGIN USERNAME KE INTERNAL
                String data = "0";
                File internalDir = getFilesDir();
                File file = new File(internalDir, "datalogin.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    osw.write(data);
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //DATA TELAH DISIMPAN KE INTERNAL
                Intent intent = new Intent(activity_Admin.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void tampilData() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Child "Pesanan" ada dalam database
                    listUser = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        ModelUser user = item.getValue(ModelUser.class);
                        user.setKey(item.getKey());
                        listUser.add(user);
                    }

                    adapterUser = new adapterUser(listUser, activity_Admin.this);
                    tv_tampil.setAdapter(adapterUser);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}