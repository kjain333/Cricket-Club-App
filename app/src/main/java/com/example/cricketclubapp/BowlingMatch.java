package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowlingMatch extends AppCompatActivity {
    Spinner spinner;
    List<String> username = new ArrayList<>();
    List<String> id = new ArrayList<>();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    ArrayAdapter<String> adapter;
    String selectedName,selectedId;
    TextView runs,balls,wickets;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling_match);
        spinner = findViewById(R.id.spinner10);
        runs = findViewById(R.id.matchBowlerRun);
        balls = findViewById(R.id.matchBowlerBalls);
        submit = findViewById(R.id.matchBowlerSubmit);
        wickets = findViewById(R.id.matchBowlerWickets);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, username);
        collectionReference.orderBy("username").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    id.add(documentSnapshot.getId());
                    String user = documentSnapshot.get("username").toString();
                    String userID = documentSnapshot.getId();
                    username.add(user);
                    id.add(userID);
                }
                adapter.notifyDataSetChanged();
            }
        });
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                selectedId = id.get(position);
                selectedName = username.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = runs.getText().toString();
                String data1 = balls.getText().toString();
                String data2 = wickets.getText().toString();
                if(data.equals("") || data1.equals("") || data2.equals(""))
                    Toast.makeText(getBaseContext(),"Please enter all details",Toast.LENGTH_LONG).show();
                else
                {
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",selectedName);
                    map.put("id",selectedId);
                    map.put("runs",data);
                    map.put("balls",data1);
                    map.put("wickets",data2);
                    FirebaseFirestore.getInstance().collection("matchBowler").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getBaseContext(),"Data Added!",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Cannot add data!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}