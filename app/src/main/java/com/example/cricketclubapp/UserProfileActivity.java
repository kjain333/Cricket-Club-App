package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    TextView profileUserName, profileHostel, profileProgramme, profileSpeciality;
    CircleImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        profileUserName = (TextView) findViewById(R.id.profileUserName);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        profileHostel = (TextView) findViewById(R.id.profileHostel);
        profileProgramme = (TextView) findViewById(R.id.profileProgramme);
        profileSpeciality = (TextView) findViewById(R.id.profileSpeciality);
        profilepic = findViewById(R.id.profilepic);
        Intent intent = getIntent();
        profileUserName.setText(intent.getStringExtra("name"));
        profileProgramme.setText(intent.getStringExtra("programme"));
        profileHostel.setText(intent.getStringExtra("hostel"));
        profileSpeciality.setText(intent.getStringExtra("speciality"));

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        getUserInfo();
    }

    private void getUserInfo() {
        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                if(value.getData().containsKey("image"))
                {
                    String image = value.get("image").toString();
                    Picasso.get().load(image).into(profilepic);
                }
            }
        });
    }

    public void logoutUser(View view){
        new AlertDialog.Builder(this)
                .setTitle("CONFIRM")
                .setMessage("Are you sure that you want to log out?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Cancel", null)
                .show();
    }

}