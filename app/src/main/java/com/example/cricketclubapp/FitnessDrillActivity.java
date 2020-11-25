package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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
   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_drill);

        mAuth = FirebaseAuth.getInstance();
        first = (Spinner) findViewById(R.id.userSpinner1);
        second = (Spinner) findViewById(R.id.userSpinner2);
        third = (Spinner)findViewById(R.id.userSpinner3);

        firstAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, allUsers);
        first.setAdapter(firstAdapter);
        second.setAdapter(firstAdapter);
        third.setAdapter(firstAdapter);


        /*databaseReference.addValueEventListener(new ValueEventListener() {
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
        });*/
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    String username = documentSnapshot.get("username").toString();
                    allUsers.add(username);
                }
                firstAdapter.notifyDataSetChanged();
            }
        });
        first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                winner = adapterView.getItemAtPosition(i).toString();
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                        {
                            if(documentSnapshot.get("username").equals(winner)) {
                                currPoints1 = Integer.valueOf(documentSnapshot.get("points").toString());
                                return;
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                runnerUp = adapterView.getItemAtPosition(i).toString();
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                        {
                            if(documentSnapshot.get("username").equals(runnerUp)) {
                                currPoints2 = Integer.valueOf(documentSnapshot.get("points").toString());
                                return;
                            }
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        third.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secondRunnerUp = adapterView.getItemAtPosition(i).toString();
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                        {
                            if(documentSnapshot.get("username").equals(secondRunnerUp)) {
                                currPoints3 = Integer.valueOf(documentSnapshot.get("points").toString());
                                return;
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
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
        });*/

        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
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
        });*/
        /*firebaseFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(), "Some Error has Occurred", Toast.LENGTH_SHORT).show();
                else
                {
                    for(DocumentSnapshot documentSnapshot: value.getDocuments())
                    {
                        String username = documentSnapshot.get("username").toString();
                        if(username.equals("abcd"))
                        {
                            currPoints1 = (Integer) documentSnapshot.get("points");
                            Log.d("dynamic",currPoints1.toString()+"hello");
                        }
                    }
                }

            }
        });
        firebaseFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(), "Some Error has Occurred", Toast.LENGTH_SHORT).show();
                else
                {
                    for(DocumentSnapshot documentSnapshot: value.getDocuments())
                    {
                        String username = documentSnapshot.get("username").toString();
                        if(username.equals(runnerUp))
                        {
                            currPoints2 = (Integer) documentSnapshot.get("points");
                        }
                    }
                }

            }
        });
        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
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
        */
        /*firebaseFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(), "Some Error has Occurred", Toast.LENGTH_SHORT).show();
                else
                {
                    for(DocumentSnapshot documentSnapshot: value.getDocuments())
                    {
                        String username = documentSnapshot.get("username").toString();
                        if(username.equals(secondRunnerUp))
                        {
                            currPoints3 = (Integer) documentSnapshot.get("points");
                        }
                    }
                }

            }
        });*/
    }

    public void submitSprintResults(View view){
//          currPoints1 += 30;
//          currPoints2 += 20;
//          currPoints3 += 10;


       /* databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {                       //Minor bug: need to finalize the results first!
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
        });*/
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                    String username = documentSnapshot.get("username").toString();
                    if (username.equals(winner)) {
                        documentSnapshot.getReference().update("points", currPoints1 + 30);
                    } else if (username.equals(runnerUp)) {
                        documentSnapshot.getReference().update("points", currPoints2 + 20);
                    } else if (username.equals(secondRunnerUp)) {
                        documentSnapshot.getReference().update("points", currPoints3 + 10);
                    }
                }
            }
        });
          Toast.makeText(getApplicationContext(), winner+secondRunnerUp+runnerUp+currPoints1.toString() + " " + currPoints2.toString() + " " + currPoints3.toString(), Toast.LENGTH_SHORT).show();

    }
}