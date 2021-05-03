package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateLiveMatchScore extends AppCompatActivity {
    EditText a,b,c,d,e,f,g,h,i,j;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_live_match_score);
        a = findViewById(R.id.textView2);
        b = findViewById(R.id.textView3);
        c = findViewById(R.id.textView4);
        d = findViewById(R.id.textView5);
        e = findViewById(R.id.textView6);
        f = findViewById(R.id.textView7);
        g = findViewById(R.id.textView8);
        h = findViewById(R.id.textView8);
        i = findViewById(R.id.textView9);
        j = findViewById(R.id.textView10);
        update = findViewById(R.id.button10);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> data = new HashMap<>();
                data.put("battingTeam",a.getText().toString());
                data.put("bowlingTeam",b.getText().toString());
                data.put("runs",c.getText().toString());
                data.put("wickets",d.getText().toString());
                data.put("overs",e.getText().toString());
                data.put("firstBatsmanName",f.getText().toString());
                data.put("firstBatsmanScore",g.getText().toString());
                data.put("secondBatsmanName",h.getText().toString());
                data.put("secondBatsmanScore",i.getText().toString());
                data.put("target",j.getText().toString());
                FirebaseFirestore.getInstance().collection("matchScore").document("6Zi758UlT1ruNem97kZC").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(),"Data Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"There was some error please try later",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}