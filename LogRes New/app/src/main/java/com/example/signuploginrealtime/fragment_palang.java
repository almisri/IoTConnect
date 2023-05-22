package com.example.signuploginrealtime;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
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
 * Use the {@link fragment_palang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_palang extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference switchref = database.getReference("users/Ahmad Ramli/Projek/IoT");
    Switch switch_buka;
    ImageButton button_manual,button_kiri,button_kanan;

    int kondisi_saklar;
    boolean switchState = false;

    //    FUNGSI UNTUK MEMBACA KONDISI SAKLAR
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

    public fragment_palang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_palang.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_palang newInstance(String param1, String param2) {
        fragment_palang fragment = new fragment_palang();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_fragment_palang, container, false);

        switch_buka=view.findViewById(R.id.switch_buka_palang);


        ambil_data_switch("Palang/buka_palang",switch_buka);

        return view;
    }
}