package com.example.cricketclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class LiveMatchScore extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_scores);
        tv1 = findViewById(R.id.teamsTV);
        tv2 = findViewById(R.id.battingTeam);
        tv3 = findViewById(R.id.runs);
        tv4 = findViewById(R.id.overs);
        tv5 = findViewById(R.id.target);
        tv6 = findViewById(R.id.batsmanName1);
        tv7 = findViewById(R.id.batsmanScore1);
        tv8 = findViewById(R.id.batsmanName2);
        tv9 = findViewById(R.id.batsmanScore2);
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
                    if(target=="")
                        tv5.setText("1st innings");
                    else
                        tv5.setText("target: "+target);
                    tv1.setText(battingTeam+" vs "+bowlingTeam);
                    tv2.setText(battingTeam);
                    tv3.setText(runs+"/"+wickets);
                    tv4.setText(overs);
                    tv6.setText(batsman1);
                    tv7.setText(batsman1score);
                    tv8.setText(batsman2);
                    tv9.setText(batsman2score);
                    //Toast.makeText(getBaseContext(),batsman1+target+batsman1score+batsman2+batsman2score+battingTeam+bowlingTeam+runs+overs+wickets,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}