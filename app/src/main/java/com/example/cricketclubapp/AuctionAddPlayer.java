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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionAddPlayer extends AppCompatActivity {
    Spinner players,teams;
    List<String> users = new ArrayList<>();
    List<String> id = new ArrayList<>();
    List<String> teamNames = new ArrayList<>();
    EditText price;
    Button button;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    CollectionReference collectionReference1 = firebaseFirestore.collection("auctionTeams");
    CollectionReference collectionReference2 = firebaseFirestore.collection("auctionPlayers");
    ArrayAdapter<String> adapter,adapter1;
    String selectedPlayer,selectedPlayerId,selectedTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_add_player);
        players = findViewById(R.id.spinner3);
        teams = findViewById(R.id.spinner4);
        price = findViewById(R.id.auctionPrice);
        button = findViewById(R.id.button9);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, users);
        adapter1 = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, teamNames);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    id.add(documentSnapshot.getId());
                    String username = documentSnapshot.get("username").toString();
                    users.add(username);
                }
                adapter.notifyDataSetChanged();
            }
        });
        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments())
                {
                    teamNames.add(documentSnapshot.getId());
                }
                adapter1.notifyDataSetChanged();
            }
        });
        players.setAdapter(adapter);
        teams.setAdapter(adapter1);
        players.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ids) {
                selectedPlayer = users.get(position);
                selectedPlayerId = id.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ids) {
                selectedTeam = teamNames.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPlayer!=null&&selectedTeam!=null&&price.getText()!=null)
                {
                    final Map<String,Object> auctionedPlayer = new HashMap<>();
                    auctionedPlayer.put("playerId",selectedPlayerId);
                    auctionedPlayer.put("playerName",selectedPlayer);
                    auctionedPlayer.put("price",price.getText().toString());
                    auctionedPlayer.put("time", System.currentTimeMillis());
                    collectionReference1.document(selectedTeam).collection("players").add(auctionedPlayer).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            collectionReference1.document(selectedTeam).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.contains("totalPlayers")&&documentSnapshot.contains("totalMoney"))
                                    {
                                        Map<String,Object> data = new HashMap<>();
                                        data.put("totalPlayers",Integer.parseInt(documentSnapshot.get("totalPlayers").toString())+1);
                                        data.put("totalMoney",Integer.parseInt(documentSnapshot.get("totalMoney").toString())+Integer.parseInt(price.getText().toString()));
                                        collectionReference1.document(selectedTeam).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                auctionedPlayer.put("team",selectedTeam);
                                                collectionReference2.add(auctionedPlayer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(getApplicationContext(),"Data Added Successfully",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Data cannot be updated",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Map<String,Object> data = new HashMap<>();
                                        data.put("totalPlayers",1);
                                        data.put("totalMoney",Integer.parseInt(price.getText().toString()));
                                        data.put("budget",200000);
                                        collectionReference1.document(selectedTeam).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                auctionedPlayer.put("team",selectedTeam);
                                                collectionReference2.add(auctionedPlayer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(getApplicationContext(),"Data Added Successfully",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Data cannot be updated",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Data cannot be updated",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Please select All Fields",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}