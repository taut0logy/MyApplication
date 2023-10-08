package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tv=findViewById(R.id.tvSplash);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.welcome_anim);
        animation.setStartOffset(500);
        tv.startAnimation(animation);
        Intent i=new Intent(SplashActivity.this,LoginActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(i);
                finish();
            }
        },2500);
    }
}