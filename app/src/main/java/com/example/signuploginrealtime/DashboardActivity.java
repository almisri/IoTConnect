package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    Button Smarthomebtn, Smartfarmbtn, Smartfishingbtn, Custombtn;
    TextView Homeicon, Monitoringicon, Profileicon;
    ViewPager2 viewPager2   ;
    private String nama, email, notelp, pass, fotoprofile;

    private Handler slideHandler = new Handler();

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewPager2 = findViewById(R.id.viewPager);

        List<SlideIten> sliderItem = new ArrayList<>();
        sliderItem.add(new SlideIten(R.drawable.carousel1));
        sliderItem.add(new SlideIten(R.drawable.carousel2));


        viewPager2.setAdapter(new SlideAdapter(sliderItem,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1- Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });



        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 2000);
            }
        });


        Homeicon = findViewById(R.id.homeicon);
        Monitoringicon = findViewById(R.id.monitoringicon);
        Profileicon = findViewById(R.id.profileicon);

        Smarthomebtn = findViewById(R.id.smarthomebtn);
        Smartfarmbtn = findViewById(R.id.smartfarmbtn);
        Smartfishingbtn = findViewById(R.id.smartfishingbtn);
        Custombtn = findViewById(R.id.custombtn);


        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nama = extra.getString("name");
            email = extra.getString("email");
            notelp = extra.getString("notelp");
            pass = extra.getString("password");
            fotoprofile = extra.getString("Foto");
        }

        Smarthomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SmartHomeActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

        Smartfarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SmartFarmActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

        Smartfishingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SmartFishingActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

        Custombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

//        Homeicon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
//                intent.putExtra("name", nama);
//                intent.putExtra("email", email);
//                intent.putExtra("notelp", notelp);
//                intent.putExtra("password", pass);
//                intent.putExtra("Foto", fotoprofile);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });

        Monitoringicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DashboardToMonitoringActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

        Profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("name", nama);
                intent.putExtra("email", email);
                intent.putExtra("notelp", notelp);
                intent.putExtra("password", pass);
                intent.putExtra("Foto", fotoprofile);
                startActivity(intent);
            }
        });

    }



    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 3000);
    }
}