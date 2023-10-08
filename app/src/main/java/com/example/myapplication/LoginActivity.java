package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    
    static int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        //User user=new User("Raufun","raufun.ahsan@gmail.com","dfdvv");
        //id=id+1;
        //add user to database
        //database.getReference().child("users").child(String.valueOf(id)).setValue(user);

        etEmail=findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v-> {
            String email= etEmail.getText().toString();
            String password= etPassword.getText().toString();
            User newUser=new User("Ahsan",email,password);
            id=id+1;
            database.getReference().child("users").child(String.valueOf(id)).setValue(newUser);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        TextView tv=findViewById(R.id.tv_register);
        //DataSnapshot dataSnapshot= database.getReference().child("users").child("1").get().getResult();
        //System.out.println(dataSnapshot.getValue(User.class));
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.child("users").child("1").getValue(User.class);
                assert user != null;
                tv.setText(user.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }
}