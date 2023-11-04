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

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionbar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
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
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        User user= (User) getIntent().getParcelableExtra("Extra_user");
        assert user != null;
        Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT).show();
        NavigationBarView navigationBarView=findViewById(R.id.bottomNav);
        navigationBarView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id==R.id.user) {
                actionbar.setTitle("User");
                actionbar.setSubtitle("My App");
                Toast.makeText(ProfileActivity.this,"Home",Toast.LENGTH_SHORT).show();
            } else if(id==R.id.messages) {
                actionbar.setTitle("Messages");
                actionbar.setSubtitle("My App");
                Toast.makeText(ProfileActivity.this,"Messages",Toast.LENGTH_SHORT).show();
            } else if(id==R.id.profile) {
                actionbar.setTitle("Profile");
                actionbar.setSubtitle("My App");
                Toast.makeText(ProfileActivity.this,"Profile",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
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