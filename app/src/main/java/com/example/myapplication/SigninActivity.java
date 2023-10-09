package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private EditText etName,etEmail,etPassword,etConfirmPassword;
    private Button btnSignin;
    private TextView tvSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        db=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        tvSignin=findViewById(R.id.tvSignIn);
        btnSignin =findViewById(R.id.button_signin);
        btnSignin.setOnClickListener(v-> {
            String name= etName.getText().toString();
            if(name.isEmpty()) {
                etName.setError("Name is required.");
                return;
            }
            String email= etEmail.getText().toString();
            if(!validateEmail(email)) {
                return;
            }
            String password= etPassword.getText().toString();
            if(!validatePassword(password)) {
                return;
            }
            String confirmPassword= etConfirmPassword.getText().toString();
            if(confirmPassword.isEmpty()) {
                etConfirmPassword.setError("Confirm Password.");
                return;
            }
            if(!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match.");
                return;
            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show();
                    if(mAuth.getCurrentUser()!=null) {
                        String uid=mAuth.getCurrentUser().getUid();
                        User user=new User(name,email,password);
                        db.getReference().child("users").child(uid).setValue(user);
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()) {
                                Toast.makeText(this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(SigninActivity.this,MainActivity.class);
                                i.putExtra("Extra_user",user);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private Boolean validateEmail(String email) {
        if(email.isEmpty()) {
            etEmail.setError("Email is required.");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(String password) {
        if(password.isEmpty()) {
            etPassword.setError("Password is required.");
            return false;
        } else {
            int passwordLength=password.length();
            if(passwordLength<6) {
                etPassword.setError("Password must be at least 6 characters.");
                return false;
            }
            int capitalLetterCount=0,smallLetterCount=0,digitCount=0;
            for(int i=0;i<passwordLength;i++) {
                char ch=password.charAt(i);
                if(ch>='A' && ch<='Z') {
                    capitalLetterCount++;
                } else if(ch>='a' && ch<='z') {
                    smallLetterCount++;
                } else if(ch>='0' && ch<='9') {
                    digitCount++;
                }
            }
            if(capitalLetterCount==0 || smallLetterCount==0) {
                etPassword.setError("Password must contain at least one capital letter and one digit.");
                return false;
            }
            etPassword.setError(null);
            return true;
        }
    }

}