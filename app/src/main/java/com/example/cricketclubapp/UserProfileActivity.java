package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView profileUserName, profileHostel, profileProgramme, profileSpeciality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        profileUserName = (TextView) findViewById(R.id.profileUserName);
        profileHostel = (TextView) findViewById(R.id.profileHostel);
        profileProgramme = (TextView) findViewById(R.id.profileProgramme);
        profileSpeciality = (TextView) findViewById(R.id.profileSpeciality);
        Intent intent = getIntent();
        profileUserName.setText(intent.getStringExtra("name"));
        profileProgramme.setText(intent.getStringExtra("programme"));
        profileHostel.setText(intent.getStringExtra("hostel"));
        profileSpeciality.setText(intent.getStringExtra("speciality"));
    }

    public void logoutUser(View view){
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}