package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class update_activity extends AppCompatActivity {

    private EditText nameupd,mailupd,phoneupd,passupd;
    private Button btnupd;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nameupd=findViewById(R.id.et_nameupd);
        mailupd=findViewById(R.id.et_emailupd);
        phoneupd=findViewById(R.id.et_phonenoupd);
        passupd=findViewById(R.id.et_passupd);
        btnupd=findViewById(R.id.btn_update);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        String uid=auth.getCurrentUser().getUid();
        DatabaseReference ref=database.getReference().child("users").child(uid);
        Map<String,Object> updates=new HashMap<>();
        btnupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=passupd.getText().toString();
                FirebaseUser user=auth.getCurrentUser();
                AuthCredential credential= EmailAuthProvider.getCredential(user.getEmail(),pass);
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        if(!nameupd.getText().toString().isEmpty()) {
                            updates.put("name",nameupd.getText().toString());
                        }
                        if(!mailupd.getText().toString().isEmpty()) {
                            updates.put("email",mailupd.getText().toString());
                        }
                        if(!phoneupd.getText().toString().isEmpty()) {
                            updates.put("phone",phoneupd.getText().toString());
                        }
                        ref.updateChildren(updates).addOnCompleteListener(task2 -> {
                            if(task2.isSuccessful()) {
                                Toast.makeText(update_activity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(update_activity.this, "Update Failed: "+task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(update_activity.this,ProfileActivity.class));
                        finish();
                    } else {
                        Toast.makeText(update_activity.this, "Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });
    }
}