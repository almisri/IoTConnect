package com.example.signuploginrealtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_pagar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_pagar extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference switchref = database.getReference("users/Ahmad Ramli/Projek/IoT/");
    Switch switch_buka,switch_tutup,switch_manual;
    ImageButton button_manual,button_kiri,button_kanan;

    int kondisi_saklar;
    boolean switchState = false;

    //FUNGSI UNTUK MEMBACA KONDISI SAKLAR
    public void ambil_data_switch(String Child,Switch saklar){
        switchref.child(Child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Mendapatkan nilai dari data yang diambil
                kondisi_saklar = dataSnapshot.getValue(Integer.class);
                // Menetapkan nilai saklar sesuai dengan nilai yang dibaca dari Firebase
                if (kondisi_saklar == 1) {
                    saklar.setChecked(true);
                } else {
                    saklar.setChecked(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Gagal membaca nilai dari database
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        saklar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switchState = true;
                    switchref.child(Child).setValue(1);
                } else {
                    switchState = false;
                    switchref.child(Child).setValue(0);
                }
            }
        });
    }
    //PENUTUP FUNGSI UNTUK MEMBACA KONDISI SAKLAR

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_pagar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_pagar.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_pagar newInstance(String param1, String param2) {
        fragment_pagar fragment = new fragment_pagar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_fragment_pagar, container, false);

        switch_buka=view.findViewById(R.id.switch_buka_pagar);
        switch_tutup=view.findViewById(R.id.switch_tutup_pagar);
        switch_manual=view.findViewById(R.id.switch_manual);
        button_kanan=view.findViewById(R.id.button_kanan);
        button_kiri=view.findViewById(R.id.button_kiri);

        ambil_data_switch("Pagar/buka_pagar",switch_buka);
        ambil_data_switch("Pagar/tutup_pagar",switch_tutup);
        ambil_data_switch("Pagar/mode_manual",switch_manual);
        switch_buka.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_tutup.setChecked(false);
                    switchref.child("Pagar/mode_manual").setValue(0);
                    switchref.child("Pagar/tutup_pagar").setValue(0);
                    switchref.child("Pagar/buka_pagar").setValue(1);
                }else{
                    switchref.child("Pagar/buka_pagar").setValue(0);
                }
            }
        });

        switch_tutup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_buka.setChecked(false);
                    switchref.child("Pagar/mode_manual").setValue(0);
                    switchref.child("Pagar/buka_pagar").setValue(0);
                    switchref.child("Pagar/tutup_pagar").setValue(1);
                }else{
                    switchref.child("Pagar/tutup_pagar").setValue(0);
                }
            }
        });
        switch_manual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_buka.setChecked(false);
                    switch_tutup.setChecked(false);
                    switchref.child("Pagar/buka_pagar").setValue(0);
                    switchref.child("Pagar/tutup_pagar").setValue(0);
                    switchref.child("Pagar/mode_manual").setValue(1);
                    button_kiri.setVisibility(View.VISIBLE);
                    button_kanan.setVisibility(View.VISIBLE);
                }else{
                    switchref.child("Pagar/mode_manual").setValue(0);
                    button_kiri.setVisibility(View.GONE);
                    button_kanan.setVisibility(View.GONE);
                    switchref.child("Pagar/manual_kiri").setValue(0);
                    switchref.child("Pagar/manual_kanan").setValue(0);
                }
            }
        });

        button_kanan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // saat button ditekan, set background ke biru
                        button_kanan.setBackgroundResource(R.drawable.blue_kanan);
                        switchref.child("Pagar/manual_kanan").setValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        // saat button dilepas, kembalikan background ke semula
                        button_kanan.setBackgroundResource(R.drawable.kanan);
                        switchref.child("Pagar/manual_kanan").setValue(0);
                        break;
                }
                return true;
            }
        });

        button_kiri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // saat button ditekan, set background ke biru
                        button_kiri.setBackgroundResource(R.drawable.blue_kiri);
                        switchref.child("Pagar/manual_kiri").setValue(1);

                        break;
                    case MotionEvent.ACTION_UP:
                        // saat button dilepas, kembalikan background ke semula
                        button_kiri.setBackgroundResource(R.drawable.kiri);
                        switchref.child("Pagar/manual_kiri").setValue(0);
                        break;
                }
                return true;
            }
        });
        return view;
    }
}