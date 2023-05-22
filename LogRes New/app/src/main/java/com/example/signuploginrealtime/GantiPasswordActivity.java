package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GantiPasswordActivity extends AppCompatActivity{

    EditText editPassword;
    Button saveButton;
    String nameUser, emailUser, notelpUser, passwordUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ganti_password);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editPassword = findViewById(R.id.editPasssword);
        saveButton = findViewById(R.id.saveButton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordChanged()){
                    Toast.makeText(GantiPasswordActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GantiPasswordActivity.this, ProfileActivity.class));
                } else {
                    Toast.makeText(GantiPasswordActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())){
            reference.child(nameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData(){
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        notelpUser = intent.getStringExtra("notelp");
        passwordUser = intent.getStringExtra("password");

        editPassword.setText(passwordUser);
    }
}