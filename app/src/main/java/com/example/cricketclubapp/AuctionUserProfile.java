package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuctionUserProfile extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4;
    CircleImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_user_profile);
        tv1 = findViewById(R.id.profileUserName2);
        tv2 = findViewById(R.id.profileSpeciality2);
        tv3 = findViewById(R.id.profileHostel2);
        tv4 = findViewById(R.id.profileProgramme2);
        profilepic = findViewById(R.id.profilepic2);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        FirebaseFirestore.getInstance().collection("users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    tv1.setText(documentSnapshot.get("username").toString());
                    tv2.setText(documentSnapshot.get("speciality").toString());
                    tv3.setText(documentSnapshot.get("hostel").toString());
                    tv4.setText(documentSnapshot.get("programme").toString());
                if(documentSnapshot.getData().containsKey("image"))
                {
                    String image = documentSnapshot.get("image").toString();
                    Picasso.get().load(image).into(profilepic);
                }
            }
        });

    }
}