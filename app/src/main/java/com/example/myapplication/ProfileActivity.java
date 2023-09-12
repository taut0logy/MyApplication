package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        actionbar=getSupportActionBar();
        if(actionbar!=null) {
            actionbar.setTitle("Profile");
            actionbar.setSubtitle("My App");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.search) {
            Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
        } else if(id==R.id.about) {
            Toast.makeText(this,"Created by me",Toast.LENGTH_SHORT).show();
        } else if(id==R.id.logout) {
            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}