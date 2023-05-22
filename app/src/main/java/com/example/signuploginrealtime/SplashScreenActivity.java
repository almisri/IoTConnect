package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplashScreenActivity extends AppCompatActivity {
//    DatabaseReference loginHistory = FirebaseDatabase.getInstance().getReference("login history/name");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    String userName;
//    TextView namapk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // timer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        namapk=findViewById(R.id.namaapk);
                        File internalDir = getFilesDir();
                        File file = new File(internalDir, "datalogin.txt");
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);

                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                stringBuilder.append(line);
                            }

                            br.close();

                            String data = stringBuilder.toString();
                            userName= data;
//                            namapk.setText(data);

                            DatabaseReference referenceAdmin = FirebaseDatabase.getInstance().getReference("admin");
                            Query checkUserDatabaseadmin = referenceAdmin.orderByChild("name").equalTo(userName);
                            checkUserDatabaseadmin.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        Intent intent = new Intent(SplashScreenActivity.this, activity_Admin.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else{

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            if(!userName.equals("0")){
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child(userName).hasChild("Pesanan")) {
                                            String nameFromDB = snapshot.child(userName).child("name").getValue(String.class);
                                            String emailFromDB = snapshot.child(userName).child("email").getValue(String.class);
                                            String notelpFromDB = snapshot.child(userName).child("notelp").getValue(String.class);
                                            String passwordFromDB = snapshot.child(userName).child("password").getValue(String.class);
                                            String fotoprofil = snapshot.child(userName).child("foto").getValue(String.class);

                                            Intent intent = new Intent(SplashScreenActivity.this, DashboardToMonitoringActivity.class);

                                            intent.putExtra("name", nameFromDB);
                                            intent.putExtra("email", emailFromDB);
                                            intent.putExtra("notelp", notelpFromDB);
                                            intent.putExtra("password", passwordFromDB);
                                            intent.putExtra("Foto", fotoprofil);

                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            String nameFromDB = snapshot.child(userName).child("name").getValue(String.class);
                                            String emailFromDB = snapshot.child(userName).child("email").getValue(String.class);
                                            String notelpFromDB = snapshot.child(userName).child("notelp").getValue(String.class);
                                            String passwordFromDB = snapshot.child(userName).child("password").getValue(String.class);
                                            String fotoprofil = snapshot.child(userName).child("foto").getValue(String.class);

                                            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);

                                            intent.putExtra("name", nameFromDB);
                                            intent.putExtra("email", emailFromDB);
                                            intent.putExtra("notelp", notelpFromDB);
                                            intent.putExtra("password", passwordFromDB);
                                            intent.putExtra("Foto", fotoprofil);

                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else if (userName.equals("0")) {
                                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
//                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
//                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }

//
                            if (userName==null){

                            }else{

                            }

                    }
                }, 500L);
            }
        }, 500L);
    }
}