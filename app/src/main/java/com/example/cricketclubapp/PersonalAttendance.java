package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonalAttendance extends AppCompatActivity {
    ListView names;
    Map<String, Integer> map = new HashMap<>();
    ArrayList<String> attendance = new ArrayList<String>();
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_attendance);
        names = (ListView) findViewById(R.id.personalAttendance);
        myAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view_layout, R.id.listContent, attendance);
        FirebaseFirestore.getInstance().collection("attendance").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    List data = (List) documentSnapshot.get("present");
                    if(data==null)
                        data = new ArrayList();
                    for(int i=0;i<data.size();i++)
                    {
                        if(map.containsKey(data.get(i).toString()))
                        {
                            Integer days = map.get(data.get(i).toString());
                            days = days+1;
                            map.put(data.get(i).toString(),days);
                        }
                        else
                        {
                            map.put(data.get(i).toString(),1);
                        }
                    }
                }
                for (final Map.Entry<String,Integer> entry : map.entrySet())
                {
                    FirebaseFirestore.getInstance().collection("users").document(entry.getKey()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            attendance.add(documentSnapshot.get("username").toString()+": "+entry.getValue().toString());
                            myAdapter.notifyDataSetChanged();
                            names.setAdapter(myAdapter);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(),"Some Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });

    }
}



