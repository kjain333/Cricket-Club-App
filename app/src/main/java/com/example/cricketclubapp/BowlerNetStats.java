package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
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

    List<String> names = new ArrayList<>();
    List<String> id = new ArrayList<>();
    Spinner spinner;
    ArrayAdapter players;
    String selectedName,selectedId;
    Button getStats;
    androidx.gridlayout.widget.GridLayout gridLayout;
    int[][] twoDimensionalArray= new int[6][3];
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowler_net_stats);
        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBarWhite));
        }
        Intent intent = getIntent();
        final String getDate = intent.getStringExtra("date");
        spinner = findViewById(R.id.bowlerspinner6);
        getStats = findViewById(R.id.bowlerbutton6);
        gridLayout = findViewById(R.id.bowlingGrid);
        tv1 = findViewById(R.id.tview1); tv2 = findViewById(R.id.tview2); tv3 = findViewById(R.id.tview3);
        tv4 = findViewById(R.id.tview4); tv5 = findViewById(R.id.tview5); tv6 = findViewById(R.id.tview6);
        tv7 = findViewById(R.id.tview7); tv8 = findViewById(R.id.tview8); tv9 = findViewById(R.id.tview9);
        tv10 = findViewById(R.id.tview10); tv11 = findViewById(R.id.tview11); tv12 = findViewById(R.id.tview12);
        tv13 = findViewById(R.id.tview13); tv14 = findViewById(R.id.tview14); tv15 = findViewById(R.id.tview15);
        tv16 = findViewById(R.id.tview16); tv17 = findViewById(R.id.tview17); tv18 = findViewById(R.id.tview18);
        final TextView[][] tvArray = {{tv1,tv2,tv3}, {tv4,tv5,tv6}, {tv7,tv8,tv9}, {tv10,tv11,tv12}, {tv13,tv14,tv15}, {tv16,tv17,tv18}};


        players = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,names);
        FirebaseFirestore.getInstance().collection("users").orderBy("username").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                    chosenDate = simpleDateFormat.parse(String.valueOf(getDate));
                    Toast.makeText(getApplicationContext(), getDate, Toast.LENGTH_SHORT).show();
                    FirebaseFirestore.getInstance().collection("stats").document(getDate).collection(selectedId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

                                for(int j=0;j<3;j++)
                                {
                                    tvArray[i][j].setText(String.valueOf(twoDimensionalArray[i][j]));
                                }
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