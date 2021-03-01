package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    Spinner bowler,shot;
    String selectedBatsman,selectedBowler,selectedLength,selectedLine,selectedShot,selectedBatsmanId,selectedBowlerId;
    List<String> lengthTypes = new ArrayList<>();
    List<String> lineTypes = new ArrayList<>();
    List<String> shotTypes = new ArrayList<>();
    List<TextView> textViews = new ArrayList<>(18);
    Button addButton,finishButton;
    TextView runW, run0, run1, run2, run3, run4, run5, run6;
    boolean isOut = false;
//    EditText runsScored;
    int runsOnThisBall=-1;
    int totalBowlsBowled=0;
    int totalRunsScored=0;
    ArrayAdapter adapter4,adapter5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_net);
        Intent intent = getIntent();
        final List<String> bowlerNames = intent.getStringArrayListExtra("names");
        final List<String> bowlerId = intent.getStringArrayListExtra("id");
        selectedBatsman = intent.getStringExtra("selectedBatsman");
        selectedBatsmanId = intent.getStringExtra("selectedBatsmanId");
        final String targetRuns = intent.getStringExtra("targetRuns");
        final String targetBowls = intent.getStringExtra("targetBowls");
        run1 = (TextView) findViewById(R.id.run1); run0 = findViewById(R.id.run0); run2 = findViewById(R.id.run2);
        run3 = findViewById(R.id.run3); run4 = findViewById(R.id.run4); run6 = findViewById(R.id.run6); runW = findViewById(R.id.runW);
        Log.d("debug",selectedBatsman);
        Log.d("debug",selectedBatsmanId);
        Log.d("debug",bowlerNames.toString());
        Log.d("debug",bowlerId.toString());
        Log.d("debug",targetRuns);
        Log.d("debug",targetBowls);
        bowler = findViewById(R.id.spinner2);
        textViews.add((TextView) findViewById(R.id.target1));
        textViews.add((TextView) findViewById(R.id.target2));
        textViews.add((TextView) findViewById(R.id.target3));
        textViews.add((TextView) findViewById(R.id.target4));
        textViews.add((TextView) findViewById(R.id.target5));
        textViews.add((TextView) findViewById(R.id.target6));
        textViews.add((TextView) findViewById(R.id.target7));
        textViews.add((TextView) findViewById(R.id.target8));
        textViews.add((TextView) findViewById(R.id.target9));
        textViews.add((TextView) findViewById(R.id.target10));
        textViews.add((TextView) findViewById(R.id.target11));
        textViews.add((TextView) findViewById(R.id.target12));
        textViews.add((TextView) findViewById(R.id.target13));
        textViews.add((TextView) findViewById(R.id.target14));
        textViews.add((TextView) findViewById(R.id.target15));
        textViews.add((TextView) findViewById(R.id.target16));
        textViews.add((TextView) findViewById(R.id.target17));
        textViews.add((TextView) findViewById(R.id.target18));
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
        adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,shotTypes);
        adapter5 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,bowlerNames);
        shot.setAdapter(adapter4);
        bowler.setAdapter(adapter5);
        bowler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBowler = bowlerNames.get(position);
                selectedBowlerId = bowlerId.get(position);
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
        for(int i=0;i<18;i++)
        {
            final int finalI = i;
            textViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedLength = lengthTypes.get(finalI /3);
                    selectedLine = lineTypes.get(finalI%3);
                    textViews.get(finalI).setText("Selected");
                    for(int i=0;i<18;i++)
                    {
                        if(i==finalI)
                            continue;
                        textViews.get(i).setText("");
                    }
                }
            });
        }
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                final String formattedString = sdf.format(date);
                final Map<String,String> map = new HashMap<>();
                if(targetBowls.equals(""))
                {
                    if(totalBowlsBowled==0)
                        map.put("mode","A");
                    else
                        map.put("mode","B");
                }
                else
                {
                    if(totalBowlsBowled==0||targetRuns=="")
                    {
                        map.put("mode","C");
                        map.put("achieved","true");
                    }
                    else
                    {
                        map.put("mode","C");
                        if(Integer.parseInt(targetRuns)/Integer.parseInt(targetBowls)<totalRunsScored/totalBowlsBowled)
                            map.put("achieved","true");
                        else
                            map.put("achieved","false");
                    }
                }
                FirebaseFirestore.getInstance().collection("challenges").document(formattedString).collection(selectedBatsmanId).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getBaseContext(),"Target: "+targetRuns+" runs in "+targetBowls+"\nAchieved: "+totalRunsScored+" runs in "+totalBowlsBowled,Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"There was some error please try later!",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d("debug",selectedBatsman+selectedBatsmanId+selectedBowler+selectedBowlerId+selectedShot);
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
                    if(runsOnThisBall != -1)
                    {
                        totalBowlsBowled++;
                        totalRunsScored+=runsOnThisBall;
                        map.put("runs",String.valueOf(runsOnThisBall));
                    }
                    Log.d("debug",map.toString());
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
        });

        run0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 0;
            }
        });
        run1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 1;
            }
        });
        run2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 2;
            }
        });
        run3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 3;
            }
        });
        run4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 4;
            }
        });
        run6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 6;
            }
        });
        runW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runsOnThisBall = 0; isOut = true;
            }
        });

    }
}