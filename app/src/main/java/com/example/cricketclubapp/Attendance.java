package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity {
    ListView names;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    CollectionReference attendanceReference = firebaseFirestore.collection("attendance");
    List<String> users = new ArrayList<>();
    List<String> id = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        names = (ListView) findViewById(R.id.attendance);
        names.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.checked_list, users);
        collectionReference.orderBy("username").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    id.add(documentSnapshot.getId());
                    String username = documentSnapshot.get("username").toString();
                    users.add(username);
                }
                for(int i=0;i<users.size();i++)
                {
                    names.setItemChecked(i,true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        names.setAdapter(adapter);
    }
    public void submitAttendance(View view){
        List<String> present = new ArrayList<>();
        SparseBooleanArray array = names.getCheckedItemPositions();
        for(int i=0;i<array.size();i++)
        {
            if(array.valueAt(i))
            {
                present.add(id.get(i));
            }
        }
        Date currentDate = Calendar.getInstance().getTime();
        Map<String,Object> presentStudents = new HashMap<>();
        presentStudents.put("present",present);
        attendanceReference.document(String.valueOf(currentDate)).set(presentStudents).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Attendance Added Successfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Attendance cannot be updated",Toast.LENGTH_LONG).show();
            }
        });
        this.finish();
    }

}