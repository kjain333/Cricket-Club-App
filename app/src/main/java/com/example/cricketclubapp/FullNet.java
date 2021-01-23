package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullNet extends AppCompatActivity {
    Spinner batsman,bowler,length,line,shot;
    String selectedBatsman,selectedBowler,selectedLength,selectedLine,selectedShot,selectedBatsmanId,selectedBowlerId;
    List<String> players = new ArrayList<>();
    List<String> playersId = new ArrayList<>();
    List<String> lengthTypes = new ArrayList<>();
    List<String> lineTypes = new ArrayList<>();
    List<String> shotTypes = new ArrayList<>();
    Button addButton,finishButton;
    ArrayAdapter adapter1,adapter2,adapter3,adapter4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_net);
        batsman = findViewById(R.id.spinner);
        bowler = findViewById(R.id.spinner2);
        length = findViewById(R.id.spinner3);
        line = findViewById(R.id.spinner4);
        shot = findViewById(R.id.spinner5);
        addButton = findViewById(R.id.button4);
        finishButton = findViewById(R.id.button5);
        lengthTypes.add("Yorker");
        lengthTypes.add("Half Volley");
        lengthTypes.add("Drive");
        lengthTypes.add("Good");
        lengthTypes.add("Short of Good");
        lengthTypes.add("Short");
        lineTypes.add("Wide");
        lineTypes.add("4-5 Stump");
        lineTypes.add("On Stumps");
        shotTypes.add("Left the ball");
        shotTypes.add("Defensive");
        shotTypes.add("Aggressive");
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,players);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,lengthTypes);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,lineTypes);
        adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,shotTypes);
        length.setAdapter(adapter2);
        line.setAdapter(adapter3);
        shot.setAdapter(adapter4);
        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<data.size();i++)
                {
                    players.add(data.get(i).get("username").toString());
                    playersId.add(data.get(i).getId());
                }
                adapter1.notifyDataSetChanged();
                batsman.setAdapter(adapter1);
                bowler.setAdapter(adapter1);
            }
        });
        batsman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBatsman = players.get(position);
                selectedBatsmanId = playersId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bowler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBowler = players.get(position);
                selectedBowlerId = playersId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLength = lengthTypes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        line.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLine = lineTypes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedShot = shotTypes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedBatsman.equals(null)||selectedBowler.equals(null)||selectedLength.equals(null)||selectedLine.equals(null)||selectedShot.equals(null))
                {
                    Toast.makeText(v.getContext(),"Please select all Fields first",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.d("debug",selectedBatsman+selectedBatsmanId+selectedBowler+selectedBowlerId+selectedLength+selectedLine+selectedShot);
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    final String formattedString = sdf.format(date);
                    final Map<String,String> map = new HashMap<>();
                    map.put("batsman",selectedBatsman);
                    map.put("bowler",selectedBowler);
                    map.put("line",selectedLine);
                    map.put("length",selectedLength);
                    map.put("shot",selectedShot);
                    FirebaseFirestore.getInstance().collection("stats").document(formattedString).collection(selectedBatsmanId).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            FirebaseFirestore.getInstance().collection("stats").document(formattedString).collection(selectedBowlerId).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getBaseContext(),"Data Added Successfully",Toast.LENGTH_LONG).show();
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(),"There was some error please try later!",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"There was some error please try later!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}