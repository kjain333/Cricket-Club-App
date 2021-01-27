package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BowlerNetStats extends AppCompatActivity {
    EditText date;
    List<String> names = new ArrayList<>();
    List<String> id = new ArrayList<>();
    Spinner spinner;
    ArrayAdapter players;
    String selectedName,selectedId;
    Button getStats;
    int[][] twoDimensionalArray= new int[6][3];
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowler_net_stats);
        date = findViewById(R.id.bowlerstatsDate);
        spinner = findViewById(R.id.bowlerspinner6);
        getStats = findViewById(R.id.bowlerbutton6);
        tableLayout = findViewById(R.id.bowlertable_main);
        players = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,names);
        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<data.size();i++)
                {
                    names.add(data.get(i).get("username").toString());
                    id.add(data.get(i).getId());
                }
                players.notifyDataSetChanged();
                spinner.setAdapter(players);
            }
        });
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<3;j++)
            {
                twoDimensionalArray[i][j] = 0;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                selectedName = names.get(position);
                selectedId = id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                for(int i=0;i<6;i++)
                {
                    for(int j=0;j<3;j++)
                    {
                        twoDimensionalArray[i][j] = 0;
                    }
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date chosenDate = null;
                try {
                    chosenDate = simpleDateFormat.parse(date.getText().toString());
                    FirebaseFirestore.getInstance().collection("stats").document(date.getText().toString()).collection(selectedId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            for(int i=0;i<documents.size();i++)
                            {
                                if(documents.get(i).get("bowler").equals(selectedName))
                                {
                                    int lengthvalue = 0,linevalue = 0;
                                    switch (documents.get(i).get("length").toString())
                                    {
                                        case "Yorker": lengthvalue = 0;
                                            break;
                                        case "Half Volley": lengthvalue = 1;
                                            break;
                                        case "Drive": lengthvalue = 2;
                                            break;
                                        case "Good": lengthvalue = 3;
                                            break;
                                        case "Short of Good": lengthvalue = 4;
                                            break;
                                        case "Short": lengthvalue = 5;
                                            break;
                                    }
                                    switch (documents.get(i).get("line").toString())
                                    {
                                        case "Wide": linevalue = 0;
                                            break;
                                        case "4-5 Stump": linevalue = 1;
                                            break;
                                        case "On Stumps": linevalue = 2;
                                            break;
                                    }
                                    twoDimensionalArray[lengthvalue][linevalue]+=1;
                                }
                            }
                            for(int i=0;i<6;i++)
                            {
                                TableRow row = new TableRow(getBaseContext());
                                for(int j=0;j<3;j++)
                                {
                                    TextView tv = new TextView(getBaseContext());
                                    tv.setMinHeight(120);
                                    tv.setMinWidth(240);
                                    tv.setGravity(Gravity.CENTER);
                                    tv.setText(String.valueOf(twoDimensionalArray[i][j]));
                                    row.addView(tv);
                                }
                                tableLayout.addView(row);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"There was some error! Please try later",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (ParseException e) {
                    Toast.makeText(getBaseContext(),"There was some error! Please Provide date in dd/MM/YYYY format",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}