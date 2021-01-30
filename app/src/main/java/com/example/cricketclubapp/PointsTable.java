package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    ListView rankings;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    ArrayList<Member> users = new ArrayList<Member>();

    CustomArrayAdapter adapter;
//    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        rankings = (ListView) findViewById(R.id.pointsTable);
        adapter = new CustomArrayAdapter(getApplicationContext(), R.layout.adapter_view_layout, users);
        collectionReference.orderBy("points", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    String username = documentSnapshot.get("username").toString();
                    String points = documentSnapshot.get("points").toString();
                    String image = documentSnapshot.get("image").toString();
                    Member newMember = new Member(username, points);
                    users.add(newMember);
                }

                adapter.notifyDataSetChanged();
            }
        });
        rankings.setAdapter(adapter);
        rankings.animate().alpha(1).setDuration(1500);
    }
}