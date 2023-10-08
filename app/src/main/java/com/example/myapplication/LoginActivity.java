package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        etEmail=findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v-> {
            String email= etEmail.getText().toString();
            String password= etPassword.getText().toString();
            if(email.isEmpty()) {
                etEmail.setError("Email is required.");
                return;
            }
            if(password.isEmpty()) {
                etPassword.setError("Password is required.");
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(this, "User logged in successfully.", Toast.LENGTH_SHORT).show();
                    if(mAuth.getCurrentUser()!=null) {
                        String uid=mAuth.getCurrentUser().getUid();
                        database.getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user=snapshot.getValue(User.class);
                                assert user != null;
                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                i.putExtra("Extra_user",user);
                                startActivity(i);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println(error);
                            }
                        });
                    }
                } else {
                    Exception e=task.getException();
                    Log.e("MyError",e.toString());
                    if(e==null) {
                        Toast.makeText(this, "Error: Unknown error occurred.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(e.getMessage().contains("password")) {
                        etPassword.setError("Invalid password.");
                        return;
                    }
                    if(e.getMessage().contains("email")) {
                        etEmail.setError("Invalid email.");
                        return;
                    }
                    if(e.getMessage().contains("no user record")) {
                        etEmail.setError("User does not exist.");
                        return;
                    }
                    if(e.getMessage().contains("network error")) {
                        Toast.makeText(this, "Error: Network error occurred.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(e.getMessage().contains("INVALID_LOGIN_CREDENTIALS")) {
                        Toast.makeText(this, "Error: Invalid login credentials.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(e.getMessage().contains("blocked")) {
                        Toast.makeText(this, "Error: Too many requests. Try again later.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            });
        });
        //User user=new User("Raufun","raufun.ahsan@gmail.com","dfdvv");
        //id=id+1;
        //add user to database
        //database.getReference().child("users").child(String.valueOf(id)).setValue(user);

        /*

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
        });*/
    }
}