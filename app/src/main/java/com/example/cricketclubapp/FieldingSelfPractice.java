package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldingSelfPractice extends AppCompatActivity {
    Button button;
    Spinner batsman;
    TextView score;
    TextView drill;
    List<String> users = new ArrayList<>();
    List<String> userId = new ArrayList<>();
    ArrayAdapter adapter;
    String selectedBatsman;
    String selectBatsmanId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fielding_self_practice);
        batsman = (Spinner) findViewById(R.id.selectFielder);
        button = (Button) findViewById(R.id.submitFieldingPractice);
        drill = (TextView) findViewById(R.id.fieldingDrill);
        score = (TextView) findViewById(R.id.fieldingScore);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,users);
        FirebaseFirestore.getInstance().collection("users").orderBy("username").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                selectedBatsman = data.get(0).get("username")!=null?data.get(0).get("username").toString():"";
                selectBatsmanId = data.get(0).getId()!=null?data.get(0).getId():"";
                for(int i=0;i<data.size();i++)
                {
                    users.add(data.get(i).get("username").toString());
                    userId.add(data.get(i).getId());
                }
                adapter.notifyDataSetChanged();
                batsman.setAdapter(adapter);
            }
        });
        batsman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBatsman = users.get(position);
                selectBatsmanId = userId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Map<String,String> map = new HashMap<>();
                map.put("user",selectedBatsman);
                map.put("score",score.getText().toString());
                map.put("drill",drill.getText().toString());
                map.put("userId",selectBatsmanId);
                FirebaseFirestore.getInstance().collection("fieldingDrill").document().set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("debug","added");
                        Toast.makeText(v.getContext(),"Data added successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(),"Failed to add data",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}