package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {

    EditText loginName, loginPassword;
    Button loginButton, googleButton;
    TextView signupRedirectText;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private GoogleSignInAccount acct;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FUllScreen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        loginName = findViewById(R.id.login_name);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.loginButton);
        googleButton = findViewById(R.id.loginGoogle);

//        acct = GoogleSignIn.getLastSignedInAccount(this);
//        if (acct!=null){
//            navigateToSecondActivity();
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                if (!validateName() | !validatePassword()){
                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    public boolean validateName(){
        String val = loginName.getText().toString();
        if (val.isEmpty()){
            loginName.setError("Name Tidak Boleh Kosong Blok!");
            return false;
        } else {
            loginName.setError(null);
            return true;
        }
    }


    public boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()){
            loginPassword.setError("password Tidak Boleh Kosong Blok!");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userName = loginName.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

//        DatabaseReference loginHistory = FirebaseDatabase.getInstance().getReference("login history/name");  //TAMBAHAN AHMAD

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){
                    loginName.setError(null);
                    String passwordFromDB = snapshot.child(userName).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        loginName.setError(null);
                        //SIMPAN DATA LOGIN USERNAME KE INTERNAL
                        String data = userName;
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



                        // pass data menggunakan intent

                        String nameFromDB = snapshot.child(userName).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userName).child("email").getValue(String.class);
                        String notelpFromDB = snapshot.child(userName).child("notelp").getValue(String.class);
                        String fotoprofil = snapshot.child(userName).child("foto").getValue(String.class);


                        reference.child(userName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("Pesanan")) {
                                    // Buka activity monitoring
                                    Intent intent = new Intent(LoginActivity.this, DashboardToMonitoringActivity.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("notelp", notelpFromDB);
                                    intent.putExtra("password", passwordFromDB);
                                    intent.putExtra("FotoProfil", fotoprofil);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    // Buka activity dashboard
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("notelp", notelpFromDB);
                                    intent.putExtra("password", passwordFromDB);
                                    intent.putExtra("FotoProfil", fotoprofil);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Penanganan kesalahan pembacaan dari Firebase Database
                            }
                        });

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("notelp", notelpFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("FotoProfil", fotoprofil);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        loginPassword.setError("Invalid Pass");
                        loginPassword.requestFocus();
                    }

                } else {
                    loginName.setError("User Belum Ada!");
                    loginName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}