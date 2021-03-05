package com.example.cricketclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LiveMatchScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_match_score);
        FirebaseFirestore.getInstance().collection("matchScore").document("6Zi758UlT1ruNem97kZC").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Toast.makeText(getBaseContext(),"There was some error",Toast.LENGTH_LONG);
                }
                else
                {
                    String battingTeam,bowlingTeam,runs,wickets,overs,batsman1,batsman1score,batsman2,batsman2score,target;
                    battingTeam = value.get("battingTeam").toString();
                    bowlingTeam = value.get("bowlingTeam").toString();
                    runs = value.get("runs").toString();
                    wickets = value.get("wickets").toString();
                    overs = value.get("overs").toString();
                    batsman1 = value.get("firstBatsmanName").toString();
                    batsman1score = value.get("firstBatsmanScore").toString();
                    batsman2 = value.get("secondBatsmanName").toString();
                    batsman2score = value.get("secondBatsmanScore").toString();
                    target = value.get("target").toString();
                    Toast.makeText(getBaseContext(),batsman1+target+batsman1score+batsman2+batsman2score+battingTeam+bowlingTeam+runs+overs+wickets,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}