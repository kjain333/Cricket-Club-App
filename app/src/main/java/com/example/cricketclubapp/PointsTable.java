package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PointsTable extends AppCompatActivity {
    ListView names;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    List<String> users = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        names = (ListView) findViewById(R.id.pointsTable);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, users);
        collectionReference.orderBy("points", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    String username = documentSnapshot.get("username").toString();
                    users.add(username);
                }

                adapter.notifyDataSetChanged();
            }
        });
        names.setAdapter(adapter);
    }
}