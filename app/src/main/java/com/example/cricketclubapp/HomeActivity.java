package com.example.cricketclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    CircleImageView profileImageView;
    TextView dashboardView;
    String name, hostel, speciality, programme;
    CardView drillsLt, netsLt,matchLt;
    RelativeLayout attendanceLt, statsLt, rankingsLt;
    private DatabaseReference databaseReference;
    private DocumentReference documentReference;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        profileImageView = findViewById(R.id.profileImageView);
        drillsLt = (CardView) findViewById(R.id.drillsLt);
        rankingsLt = findViewById(R.id.rankingsLt);
        attendanceLt = findViewById(R.id.attendanceLt);
        netsLt = (CardView) findViewById(R.id.netsLt);
        matchLt = (CardView) findViewById(R.id.matchLayout);
        statsLt = findViewById(R.id.statsLt);
        t2 = (TextView) findViewById(R.id.t2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        documentReference = firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                name = value.get("username").toString();
                hostel = value.get("hostel").toString();
                speciality = value.get("speciality").toString();
                programme = value.get("programme").toString();
                t2.setText(name);
                if (value.getData().containsKey("image")) {
                    String image = value.get("image").toString();
                    Picasso.get().load(image).into(profileImageView);
                }
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

        drillsLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DrillsActivity.class);
                startActivity(intent);
            }
        });
        matchLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MatchChoose.class);
                startActivity(intent);
            }
        });

        rankingsLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PointsTable.class);
                startActivity(intent);
            }
        });

        attendanceLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AttendanceHome.class);
                startActivity(intent);
            }
        });

        netsLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FullNetStart.class);
                startActivity(intent);
            }
        });

        statsLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
            }
        });
    }
}