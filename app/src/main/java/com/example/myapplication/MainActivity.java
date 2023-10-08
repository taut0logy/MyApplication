package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView nav;
    private Toolbar toolbar;
    private ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer=findViewById(R.id.drawer);
        nav=findViewById(R.id.navDrawer);
        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        actionbar=getSupportActionBar();
        if(actionbar!=null) {
            actionbar.setTitle("Home");
            actionbar.setSubtitle("My App");
        }
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        load(new HomeFragment());
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home) {
                    actionbar.setTitle("Home");
                    actionbar.setSubtitle("My App");
                    load(new HomeFragment());
                } else if(id==R.id.messages) {
                    actionbar.setTitle("Messages");
                    actionbar.setSubtitle("My App");
                    load(new MessageFragment());
                } else if(id==R.id.settings) {
                    actionbar.setTitle("Settings");
                    actionbar.setSubtitle("My App");
                    load(new SettingsFragment());
                } else if(id==R.id.exit) {
                    Toast.makeText(MainActivity.this,"Exit",Toast.LENGTH_SHORT).show();
                    finish();
                }
                drawer.closeDrawer(nav);
                return true;
            }
        });

        //Toolbar menu item click
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.search) {
                    Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_SHORT).show();
                } else if(id==R.id.profile) {
                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);
                } else if(id==R.id.about) {
                    Toast.makeText(MainActivity.this,"Created by me",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        load(new HomeFragment());
    }

    private void load(Fragment fragment){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frameMain,fragment);
            ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.search) {
            Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_SHORT).show();
        } else if(id==R.id.profile) {
            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if(id==R.id.about) {
            Toast.makeText(MainActivity.this,"Created by me",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(nav)) {
            drawer.closeDrawer(nav);
        }
        else
            super.onBackPressed();
    }
}