package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ImageView profileImageView;
    TextView dashboardView;
    String name,hostel,speciality,programme;
    LinearLayout drillsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        dashboardView = (TextView) findViewById(R.id.dashboardView);
        drillsLayout = (LinearLayout) findViewById(R.id.drillsLayout);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("username").getValue().toString();
                hostel = snapshot.child("hostel").getValue().toString();
                speciality = snapshot.child("speciality").getValue().toString();
                programme = snapshot.child("programme").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("hostel", hostel);
                intent.putExtra("speciality", speciality);
                intent.putExtra("programme", programme);

                startActivity(intent);
            }
        });

        drillsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), DrillsActivity.class);
                 startActivity(intent);
            }
        });



    }
}

