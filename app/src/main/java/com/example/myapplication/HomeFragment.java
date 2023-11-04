package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    private User user;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HomeFragment", "onCreate");
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        if(currentUser!=null) {
            String uid=currentUser.getUid();
            database.getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user=snapshot.getValue(User.class);
                    assert user != null;
                    Log.e("HomeFragment", "OnDataChange " + user);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("HomeFragment", "Error: " + error);
                }
            });
        } else {
            Log.e("HomeFragment", "onCreate: User is null");
        }
        Log.e("HomeFragment", "end of onCreate: " + user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("HomeFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("HomeFragment", "onViewCreated");
        TextView tvName=view.findViewById(R.id.text_home);
        if(user!=null) {
            tvName.setText("Welcome " + user.getName());
        } else {
            tvName.setText("Welcome");
        }
    }
}
