package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowlingDrillActivity extends AppCompatActivity {
    Spinner spinner;
    Spinner playerSpinner;
    List<String> users = new ArrayList<>();
    List<String> usersId = new ArrayList<>();
    String selectedName;
    String selectedId;
    EditText totalBowls;
    EditText bowlsHitTarget;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    List<String> lengthTypes = new ArrayList<>();
    Button submitButton;
    String selectedLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling_drill);
        spinner = findViewById(R.id.spinner7);
        playerSpinner = findViewById(R.id.spinner8);
        submitButton = findViewById(R.id.button7);
        totalBowls = findViewById(R.id.totalBowls);
        bowlsHitTarget = findViewById(R.id.totalTargetHit);
        lengthTypes.add("1");
        lengthTypes.add("2");
        lengthTypes.add("3");
        lengthTypes.add("4");
        lengthTypes.add("5");
        lengthTypes.add("6");
        lengthTypes.add("7");
        lengthTypes.add("8");
        lengthTypes.add("9");
        lengthTypes.add("10");
        lengthTypes.add("11");
        lengthTypes.add("12");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,lengthTypes);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,users);
        spinner.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<data.size();i++)
                {
                    users.add(data.get(i).get("username").toString());
                    usersId.add(data.get(i).getId());
                }
                adapter1.notifyDataSetChanged();
                playerSpinner.setAdapter(adapter1);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLength = lengthTypes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedName = users.get(position);
                selectedId = usersId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTotalBowls = totalBowls.getText()!=null?totalBowls.getText().toString():null;
                String selectedTotalHit = bowlsHitTarget.getText()!=null?bowlsHitTarget.getText().toString():null;
                if(selectedLength != null && selectedTotalHit != null && selectedTotalBowls != null && selectedName != null)
                {
                    Map<String,String> data = new HashMap<>();
                    data.put("name",selectedName);
                    data.put("id",selectedId);
                    data.put("totalBalls",selectedTotalBowls);
                    data.put("length",selectedLength);
                    data.put("ballsHitTarget",selectedTotalHit);
                    FirebaseFirestore.getInstance().collection("bowlingDrill").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getBaseContext(),"Data added Successfully",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Failed to add Data",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Please enter all details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}