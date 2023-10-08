package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText etEmail,etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        User user=new User("Raufun","raufun.ahsan@gmail.com","dfdvv");
        //add user to database
        database.getReference().child("users").child("1").setValue(user);

        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v-> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            User newUser=new User("Ahsan",email,password);
            database.getReference().child("users").child("2").setValue(newUser);
        });

        //DataSnapshot dataSnapshot= database.getReference().child("users").child("1").get().getResult();
        //System.out.println(dataSnapshot.getValue(User.class));
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                System.out.println(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }
}