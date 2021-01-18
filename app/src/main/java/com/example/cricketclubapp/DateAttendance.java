package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

public class DateAttendance extends AppCompatActivity {
    List<String> users = new ArrayList<>();
    ListView names;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_attendance);
        names = (ListView) findViewById(R.id.nameDateAttendance);
        Intent intent = getIntent();
        String getDate = intent.getStringExtra("date");
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_layout, R.id.listContent, users);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                simpleDateFormat.setLenient(false);
                Date startDate = new Date();
                startDate.setYear(2020);
                simpleDateFormat.set2DigitYearStart(startDate);
                Date chosendate = null;
                try {
                    chosendate = simpleDateFormat.parse(String.valueOf(getDate));
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(),"There was some error! Please Provide date in dd/MM/YYYY format",Toast.LENGTH_LONG).show();
                }
                if(chosendate!=null)
                {
                    final Date finalChosendate = chosendate;
                    FirebaseFirestore.getInstance().collection("attendance").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(final DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                                String firebasedate = documentSnapshot.getId();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                try {
                                    Date givendate = sdf.parse(firebasedate);
                                    givendate.setHours(0);
                                    givendate.setSeconds(0);
                                    givendate.setMinutes(0);
                                    Log.d("debug", String.valueOf(givendate));
                                    Log.d("debug", String.valueOf(finalChosendate));
                                    if (givendate.equals(finalChosendate)) {
                                        Log.d("debug", "reached");

                                        final List<String> present = (List<String>) documentSnapshot.get("present");
                                        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                users.clear();
                                                for (DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots.getDocuments()) {
                                                    if (present.contains(documentSnapshot1.getId())) {
                                                        String studentnames = documentSnapshot1.get("username").toString();
                                                        users.add(studentnames);
                                                    }
                                                }
                                                Log.d("debug", String.valueOf(users));

                                                adapter.notifyDataSetChanged();
                                                names.setAdapter(adapter);
                                            }
                                        });
                                    }
                                } catch (ParseException e) {
                                    Toast.makeText(getBaseContext(), "Some Error occurred!", Toast.LENGTH_SHORT).show();
                                }
                            }
//                            if(users.isEmpty()){
//                                Toast.makeText(getApplicationContext(), "Oops!! No attendance data found for this date.", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Some Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
    }
