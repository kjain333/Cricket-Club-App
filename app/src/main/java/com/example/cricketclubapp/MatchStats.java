package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchStats extends AppCompatActivity {
    Spinner spinner;
    List<String> username = new ArrayList<>();
    List<String> id = new ArrayList<>();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    ArrayAdapter<String> adapter;
    String selectedName,selectedId;
    Button submit;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_stats);
        spinner = findViewById(R.id.spinner11);
        submit = findViewById(R.id.button2);
        tableLayout = findViewById(R.id.table_match_stat);
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
                if(selectedId.equals("")||selectedId==null)
                {
                    Toast.makeText(getBaseContext(),"Please select user",Toast.LENGTH_LONG).show();
                }
                tableLayout.removeAllViews();
                FirebaseFirestore.getInstance().collection("matchBatsman").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int balls=0,runs=0,innings=0,notouts=0;
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                        for(int i=0;i<documentSnapshots.size();i++)
                        {
                            if(documentSnapshots.get(i).get("name").toString().equals(selectedName))
                            {
                                innings++;
                                runs+=Integer.parseInt(Objects.requireNonNull(documentSnapshots.get(i).get("runs")).toString());
                                balls+=Integer.parseInt(Objects.requireNonNull(documentSnapshots.get(i).get("balls")).toString());
                                if(documentSnapshots.get(i).get("notout").toString().equals("true"))
                                {
                                    notouts++;
                                }
                            }

                        }
                        if(innings>0)
                        {
                            TableRow row = new TableRow(getBaseContext());
                            TextView tv = new TextView(getBaseContext());
                            tv.setText("Batting Stats");
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Runs: "+String.valueOf(runs));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Balls: "+String.valueOf(balls));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Innings: "+String.valueOf(innings));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Not Outs: "+String.valueOf(notouts));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            tableLayout.setGravity(Gravity.CENTER);
                        }
                        Toast.makeText(getBaseContext(),"Fetch Completed!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"Cannot fetch data!",Toast.LENGTH_LONG).show();
                    }
                });
                FirebaseFirestore.getInstance().collection("matchBowler").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int balls=0,runs=0,innings=0,wickets=0;
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                        for(int i=0;i<documentSnapshots.size();i++)
                        {
                            if(documentSnapshots.get(i).get("name").toString().equals(selectedName))
                            {
                                innings++;
                                runs+=Integer.parseInt(Objects.requireNonNull(documentSnapshots.get(i).get("runs")).toString());
                                balls+=Integer.parseInt(Objects.requireNonNull(documentSnapshots.get(i).get("balls")).toString());
                                wickets+=Integer.parseInt(Objects.requireNonNull(documentSnapshots.get(i).get("wickets")).toString());
                            }

                        }
                        if(innings>0)
                        {
                            TableRow row = new TableRow(getBaseContext());
                            TextView tv = new TextView(getBaseContext());
                            tv.setText("Bowling Stats");
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Runs: "+String.valueOf(runs));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Balls: "+String.valueOf(balls));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Innings: "+String.valueOf(innings));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            row = new TableRow(getBaseContext());
                            tv = new TextView(getBaseContext());
                            tv.setText("Wickets: " + String.valueOf(wickets));
                            tv.setGravity(Gravity.CENTER);
                            tv.setPadding(10,10,10,10);
                            row.addView(tv);
                            row.setGravity(Gravity.CENTER);
                            tableLayout.addView(row);
                            tableLayout.setGravity(Gravity.CENTER);
                        }
                        Toast.makeText(getBaseContext(),"Fetch Completed!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"Cannot fetch data!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}