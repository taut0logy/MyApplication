package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

        Animation welcomeAnimation= AnimationUtils.loadAnimation(this,R.anim.welcome_anim);
        Animation rightAnimation= AnimationUtils.loadAnimation(this,R.anim.appear_from_right);
        Animation leftAnimation= AnimationUtils.loadAnimation(this,R.anim.appear_from_left);

        Button sign=findViewById(R.id.splash_Signin);
        Button reg=findViewById(R.id.splash_Register);

        sign.setVisibility(View.GONE);
        reg.setVisibility(View.GONE);

        welcomeAnimation.setStartOffset(500);
        tv.startAnimation(welcomeAnimation);

        Intent i=new Intent(SplashActivity.this,SigninActivity.class);
        Intent i2=new Intent(SplashActivity.this,MainActivity.class);
        Intent i3=new Intent(SplashActivity.this,LoginActivity.class);

        final FirebaseUser[] user = new FirebaseUser[1];

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user[0] =mAuth.getCurrentUser();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user[0]!=null) {
                            Toast.makeText(SplashActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(i2);
                            SplashActivity.this.finish();
                        }
                        else {
                            sign.setVisibility(View.VISIBLE);
                            reg.setVisibility(View.VISIBLE);
                            sign.startAnimation(leftAnimation);
                            reg.startAnimation(rightAnimation);
                            sign.setOnClickListener(v-> {
                                startActivity(i3);
                                finish();
                            });
                            reg.setOnClickListener(v-> {
                                startActivity(i);
                                SplashActivity.this.finish();
                            });
                        }
                    }
                },1500);
            }
        },1000);


    }
}