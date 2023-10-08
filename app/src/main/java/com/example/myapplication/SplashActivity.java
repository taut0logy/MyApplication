package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tv=findViewById(R.id.tvSplash);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        ImageView load=findViewById(R.id.ivLoad);

        Animation welcomeAnimation= AnimationUtils.loadAnimation(this,R.anim.welcome_anim);
        Animation spinAnimation= AnimationUtils.loadAnimation(this,R.anim.loadspin);

        welcomeAnimation.setStartOffset(500);
        tv.startAnimation(welcomeAnimation);

        Intent i=new Intent(SplashActivity.this,SigninActivity.class);
        Intent i2=new Intent(SplashActivity.this,MainActivity.class);
        final FirebaseUser[] user = new FirebaseUser[1];
        load.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user[0] =mAuth.getCurrentUser();
                load.setVisibility(View.VISIBLE);
                load.startAnimation(spinAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user[0]!=null) {
                            Toast.makeText(SplashActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(i2);
                            finish();
                        }
                        else {
                            startActivity(i);
                            finish();
                        }
                    }
                },1500);
            }
        },2000);


    }
}