package com.example.signuploginrealtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_terima#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_terima extends Fragment {

    private String data, nama;
    EditText namaActivity;
    Button btn_kirim,btnBatal;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_terima() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_terima.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_terima newInstance(String param1, String param2) {
        fragment_terima fragment = new fragment_terima();
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
        View view= inflater.inflate(R.layout.fragment_terima, container, false);
        namaActivity=view.findViewById(R.id.namaActivity);
        btn_kirim=view.findViewById((R.id.btn_kirim));
        btnBatal=view.findViewById((R.id.btnBatal));
        Bundle args = getArguments();
        if (args != null) {
            data = args.getString("key_data");
            nama = args.getString("nama");
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users/"+nama+"/Pesanan/"+data);

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Activity = namaActivity.getText().toString();
                database.child("activity").setValue(Activity);
                getParentFragmentManager().popBackStack();
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
        return view;
    }
}