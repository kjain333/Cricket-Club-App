package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class PersonalAttendance extends AppCompatActivity {
    TextView attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_attendance);
        attendance = (TextView) findViewById(R.id.personalAttendance);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore.getInstance().collection("attendance").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int presentDays = 0;
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    List data = (List) documentSnapshot.get("present");
                    if(data.contains(auth.getCurrentUser().getUid()))
                    {
                        presentDays = presentDays +1;
                    }
                }
                attendance.setText(String.valueOf(presentDays));
                //update present days here
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(),"Some Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });
    }
}