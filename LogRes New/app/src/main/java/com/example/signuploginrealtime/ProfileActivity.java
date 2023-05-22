package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.sql.Timestamp;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference loginHistory = FirebaseDatabase.getInstance().getReference("login history/name");
    TextView backIcon, profileName,  profileEmail, profileNotelp, UploadFoto;
    TextView containerPassword, containerNotif, containerTatacara;
    TextView txtChangepass, txtNotif, txtTatacara;
    TextView rightChangePass, rightNotif, rightTataCara;
    TextView HomeIcon, MonitoringIcon, ProfileIcon;
    Button SignOut;
    ImageView ImageView;
    private Uri fotoprofilLama;
    private Uri FotoProfileBaru;
    private static final int GALLERY_CODE = 1;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    private String user_name, user_email, user_notelp, user_pass, user_fotoprofile;
    private StorageReference storage;
    FirebaseDatabase database;
    DatabaseReference reference;

    private Uri Foto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        backIcon = findViewById(R.id.backIcon);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileNotelp = findViewById(R.id.profileNotelp);
        UploadFoto = findViewById(R.id.uploadFoto);

        storage = FirebaseStorage.getInstance().getReference();

        containerPassword = findViewById(R.id.containerPassword);
        containerNotif = findViewById(R.id.containerNotif);
        containerTatacara = findViewById(R.id.containerTatacara);

        txtChangepass = findViewById(R.id.txtChangepass);
        txtNotif = findViewById(R.id.txtNotif);
        txtTatacara = findViewById(R.id.txtTatacara);

        rightChangePass = findViewById(R.id.rightPass);
        rightNotif = findViewById(R.id.rightNotif);
        rightTataCara = findViewById(R.id.rightTatacara);

        HomeIcon = findViewById(R.id.homeicon);
        MonitoringIcon = findViewById(R.id.monitoringicon);
        ProfileIcon = findViewById(R.id.profileicon);

        SignOut = findViewById(R.id.btnKeluar);

        ImageView = findViewById(R.id.imageView4);


        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        user_email = intent.getStringExtra("email");
        user_notelp = intent.getStringExtra("notelp");

        //Load Gambar
        String fotoprofil = intent.getStringExtra("Foto");
        Foto = Uri.parse(fotoprofil);
        Picasso.get().load(Foto).into(ImageView);

        profileName.setText(user_name);
        profileEmail.setText(user_email);
        profileNotelp.setText(user_notelp);


        containerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        txtChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        rightChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        containerNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NotifikasiActivity.class));
            }
        });

        txtNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NotifikasiActivity.class));
            }
        });

        rightNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NotifikasiActivity.class));
            }
        });

        containerTatacara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TataCaraPenggunaanActivity.class);
                startActivity(intent);
            }
        });

        txtTatacara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TataCaraPenggunaanActivity.class);
                startActivity(intent);
            }
        });

        rightTataCara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TataCaraPenggunaanActivity.class);
                startActivity(intent);
            }
        });


        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginHistory.setValue("0");                          //TAMBAHAN AHMAD

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

                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                SignOut();
            }
        });

        HomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            }
        });

        MonitoringIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashboardToMonitoringActivity.class);
                intent.putExtra("name", user_name);
                intent.putExtra("email", user_email);
                intent.putExtra("notelp", user_notelp);
                intent.putExtra("password",user_pass );
                intent.putExtra("Foto", user_fotoprofile);
                startActivity(intent);
            }
        });

        ProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            }
        });

        UploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
            }
        });


//        showData();

    }

    private void SignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            if (data !=null){
                fotoprofilLama = data.getData(); //Mendapatkan Path Aktual
                ImageView.setImageURI(fotoprofilLama); //Menampilkan Gambar
                final StorageReference filepath = storage.child("fotoTalabaiKambangButo").child("fotoUser" + user_name);
                filepath.putFile(fotoprofilLama).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String fotoprofilpath = uri.toString();
                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("users/"+user_name+"/foto");
                                reference.setValue(fotoprofilpath);

                            }
                        });
                    }
                });
            }
        }
    }

//    private void showData(){
//        Intent intent = getIntent();
//
//        user_name = intent.getStringExtra("name");
//        String user_email = intent.getStringExtra("email");
//        String user_notelp = intent.getStringExtra("notelp");
//
//        //Load Gambar
//        String fotoprofil = intent.getStringExtra("Foto");
//        Foto = Uri.parse(fotoprofil);
//        Picasso.get().load(Foto).into(ImageView);
//
//        profileName.setText(user_name);
//        profileEmail.setText(user_email);
//        profileNotelp.setText(user_notelp);
//
//    }

    public void passUserData(){
        String user_name = profileName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(user_name);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String nameFromDB = snapshot.child(user_name).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(user_name).child("email").getValue(String.class);
                    String notelpFromDB = snapshot.child(user_name).child("notelp").getValue(String.class);
                    String passwordFromDB = snapshot.child(user_name).child("password").getValue(String.class);

                    Intent intent = new Intent(ProfileActivity.this, GantiPasswordActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("notelp", notelpFromDB);
                    intent.putExtra("password", passwordFromDB);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
