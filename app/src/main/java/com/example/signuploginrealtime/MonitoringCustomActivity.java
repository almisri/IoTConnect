package com.example.signuploginrealtime;

import static android.content.Intent.ACTION_VOICE_COMMAND;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MonitoringCustomActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tempref = database.getReference("users/Ahmad Ramli/Projek/IoT/temp_ruangan");
    DatabaseReference nama_user = database.getReference("users/Ahmad Ramli/name");
    DatabaseReference refKomp = database.getReference("users/Ahmad Ramli/Projek/IoT/");
    Calendar calendar = Calendar.getInstance();
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    ImageButton btn_pintu, btn_pagar, btn_palang, btn_arrow, btn_asisten;
    ImageView iconcuaca;
    TextView text_temp, text_profile, text_bagian;
    String ID_BahasaIndonesia = "id";
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_custom);

        btn_pintu = findViewById(R.id.button_pintu);
        btn_pagar = findViewById(R.id.button_pagar);
        btn_palang = findViewById(R.id.button_palang);
        text_temp = findViewById(R.id.text_temp);
        text_profile = findViewById(R.id.text_profile);
        text_bagian = findViewById(R.id.text_bagian);
        iconcuaca = findViewById(R.id.iconcuaca);
        btn_arrow = findViewById(R.id.arrow_button);
        btn_asisten = findViewById(R.id.button_asisten);
        btn_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_asisten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, ID_BahasaIndonesia);
                try {
                    startActivityForResult(intent, RESULT_SPEECH);

                } catch (ActivityNotFoundException e) {

                }
            }
        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == textToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.forLanguageTag(ID_BahasaIndonesia));
                }
            }
        });

        btn_pintu.setBackgroundResource(R.drawable.blue_button_pintu);
        text_bagian.setText("Bagian pintu");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new fragment_pintu());
        ft.commit();


        if (hourOfDay >= 6 && hourOfDay < 19) {
            iconcuaca.setImageResource(R.drawable.cuaca_hari_cerah);
        } else {
            iconcuaca.setImageResource(R.drawable.cuaca_malam);
        }

        tempref.addValueEventListener(new ValueEventListener() {                                // AMBIL DATA TEMP RUANGAN
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = snapshot.getValue(String.class);
                text_temp.setText(temp + "°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });                                                                                         //AMBIL DATA USER
        nama_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nama = snapshot.getValue(String.class);
                text_profile.setText("Selamat Datang, " + nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btn_pintu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pintu.setBackgroundResource(R.drawable.blue_button_pintu);
                btn_pagar.setBackgroundResource(R.drawable.button_pagar);
                btn_palang.setBackgroundResource(R.drawable.button_palang);
                text_bagian.setText("Bagian pintu");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new fragment_pintu());
                ft.commit();
            }
        });
        btn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pintu.setBackgroundResource(R.drawable.button_pintu);
                btn_pagar.setBackgroundResource(R.drawable.blue_button_pagar);
                btn_palang.setBackgroundResource(R.drawable.button_palang);
                text_bagian.setText("Bagian pagar");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new fragment_pagar());
                ft.commit();
            }
        });
        btn_palang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pintu.setBackgroundResource(R.drawable.button_pintu);
                btn_pagar.setBackgroundResource(R.drawable.button_pagar);
                btn_palang.setBackgroundResource(R.drawable.blue_button_palang);
                text_bagian.setText("Bagian palang");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, new fragment_palang());
                ft.commit();
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String ucapan = text.get(0).toLowerCase();
                    if (ucapan.equals("buka pintu") || ucapan.equals("oke google buka pintu") || ucapan.equals("ok google buka pintu") || ucapan.equals("google buka pintu") || ucapan.equals("tolong buka pintu") || ucapan.equals("open the door")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Tod, pintu di buka ", TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Pintu/buka_pintu").setValue(1);
                    } else if (ucapan.equals("tutup pintu") || ucapan.equals("oke google tutup pintu") || ucapan.equals("ok google tutup pintu") || ucapan.equals("google tutup pintu") || ucapan.equals("tolong tutup pintu") || ucapan.equals("close the door")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Tod, pintu di tutup" , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Pintu/tutup_pintu").setValue(1);
                    } else if (ucapan.equals("pintu mode auto") || ucapan.equals("aktifkan mode auto") || ucapan.equals("ok google mode auto") || ucapan.equals("ok google aktifkan pintu mode auto") || ucapan.equals("pintu otomatis") || ucapan.equals("auto mode") || ucapan.equals("otomatis") || ucapan.equals("aktifkan pintu otomatis")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Boss Ku, pintu mode auto aktif" , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Pintu/mode_auto").setValue(1);
                    } else if (ucapan.equals("buka palang") || ucapan.equals("ok google buka palang") || ucapan.equals("google buka palang") || ucapan.equals("ok google tolong buka palang") || ucapan.equals("palang buka") || ucapan.equals("palang mode buka") || ucapan.equals("open palang") || ucapan.equals("naikkan palang")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Boss Ku, palang di buka" , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Palang/buka_palang").setValue(1);
                    } else if (ucapan.equals("tutup palang") || ucapan.equals("ok google tutup palang") || ucapan.equals("google tutup palang") || ucapan.equals("ok google tolong tutup palang") || ucapan.equals("palang tutup") || ucapan.equals("palang mode tutup") || ucapan.equals("close palang") || ucapan.equals("turunkan palang")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Boss Ku, palang di tutup " , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Palang/buka_palang").setValue(0);
                    }else if (ucapan.equals("buka pagar") || ucapan.equals("oke google buka pagar") || ucapan.equals("ok google buka pagar") || ucapan.equals("google buka pagar") || ucapan.equals("tolong buka pagar") || ucapan.equals("buka pagarnya")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Boss Ku, pagar di buka" , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Pagar/buka_pagar").setValue(1);
                    }else if (ucapan.equals("tutup pagar") || ucapan.equals("oke google tutup pagar") || ucapan.equals("ok google tutup pagar") || ucapan.equals("google tutup pagar") || ucapan.equals("tolong tutup pagar") || ucapan.equals("tutup pagarnya")) {
                        textToSpeech.setLanguage(new Locale("id","ID"));
                        textToSpeech.speak("Oke Boss Ku, pagar di tutup" , TextToSpeech.QUEUE_FLUSH, null);
                        refKomp.child("Pagar/tutup_pagar").setValue(1);
                    }
                    break;
                }

        }
    }
}