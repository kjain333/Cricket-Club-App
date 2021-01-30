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
import android.widget.Toast;

import com.google.common.io.LineReader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    CircleImageView profileImageView;
    TextView dashboardView;
    String name,hostel,speciality,programme;
    LinearLayout drillsLayout, rankingsLayout, attendanceLayout, netsLayout, statsLayout;
    private DatabaseReference databaseReference;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        profileImageView = findViewById(R.id.profileImageView);
        dashboardView = (TextView) findViewById(R.id.dashboardView);
        drillsLayout = (LinearLayout) findViewById(R.id.drillsLayout);
        rankingsLayout = (LinearLayout) findViewById(R.id.rankingsLayout);
        attendanceLayout = (LinearLayout) findViewById(R.id.attendanceLayout);
        netsLayout = (LinearLayout) findViewById(R.id.nets);
        statsLayout = (LinearLayout) findViewById(R.id.statsLayout);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
       /* databaseReference.addValueEventListener(new ValueEventListener() {
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
*/
        documentReference = firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                name = value.get("username").toString();
                hostel = value.get("hostel").toString();
                speciality = value.get("speciality").toString();
                programme = value.get("programme").toString();
                if(value.getData().containsKey("image"))
                {
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

        drillsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), DrillsActivity.class);
                 startActivity(intent);
            }
        });

        rankingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PointsTable.class);
                startActivity(intent);
            }
        });

        attendanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AttendanceHome.class);
                startActivity(intent);
            }
        });

        netsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FullNet.class);
                startActivity(intent);
            }
        });

        statsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
            }
        });
    }

//    private void getUserInfo() {
//        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
//                    if(snapshot.hasChild("image")){
//                        String image = snapshot.child("image").getValue().toString();
//                        Picasso.get().load(image).into(profileImageView);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}

