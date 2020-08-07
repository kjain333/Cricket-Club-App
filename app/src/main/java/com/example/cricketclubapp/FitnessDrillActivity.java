package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FitnessDrillActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    List<String> allUsers = new ArrayList<>();
    Spinner first,second,third;
    ArrayAdapter<String> firstAdapter;
    String winner, runnerUp, secondRunnerUp;
    Integer currPoints1=0, currPoints2=0, currPoints3=0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_drill);

        mAuth = FirebaseAuth.getInstance();
        first = (Spinner) findViewById(R.id.userSpinner1);
        second = (Spinner) findViewById(R.id.userSpinner2);
        third = (Spinner)findViewById(R.id.userSpinner3);

        firstAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allUsers);
        first.setAdapter(firstAdapter);
        second.setAdapter(firstAdapter);
        third.setAdapter(firstAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot nameSnapshot: snapshot.getChildren()) {
                    String userName = nameSnapshot.child("username").getValue(String.class);
                    allUsers.add(userName);
                }

                firstAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                winner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                runnerUp = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        third.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secondRunnerUp = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue().toString() == winner){
                        currPoints1 = (Integer) dataSnapshot.child("points").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue().toString() == runnerUp){
                      currPoints2 = (Integer) dataSnapshot.child("points").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue().toString() == secondRunnerUp){
                        currPoints3 = (Integer) dataSnapshot.child("points").getValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void submitSprintResults(View view){
//          currPoints1 += 30;
//          currPoints2 += 20;
//          currPoints3 += 10;


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue().toString() == winner){
                        dataSnapshot.getRef().child("points").setValue(currPoints1 + 30);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    if(dataSnapshot.child("username").getValue().toString() == runnerUp){
                        dataSnapshot.getRef().child("points").setValue(currPoints2 + 20);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue().toString() == secondRunnerUp){
                        dataSnapshot.getRef().child("points").setValue(currPoints3 + 10);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

          Toast.makeText(getApplicationContext(), currPoints1.toString() + " " + currPoints2.toString() + " " + currPoints3.toString(), Toast.LENGTH_SHORT).show();

    };
}