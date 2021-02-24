package com.example.cricketclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AuctionTeams extends AppCompatActivity {
    TableLayout teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_teams);
        teams = findViewById(R.id.table_auction_teams);
        FirebaseFirestore.getInstance().collection("auctionTeams").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                teams.removeAllViews();
                TableRow row = new TableRow(getBaseContext());
                TextView tv = new TextView(getBaseContext());
                tv.setText("S. No.");
                tv.setTextColor(getResources().getColor(R.color.statusBarWhite));
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(10,10,10,10);
                row.addView(tv);
                TextView tv0 = new TextView(getBaseContext());
                tv0.setText("Team Name");
                tv0.setTextColor(getResources().getColor(R.color.statusBarWhite));
                tv0.setGravity(Gravity.CENTER);
                tv0.setPadding(10,10,10,10);
                row.addView(tv0);
                TextView tv1 = new TextView(getBaseContext());
                tv1.setText("Total Players");
                tv1.setTextColor(getResources().getColor(R.color.statusBarWhite));
                tv1.setPadding(10,10,10,10);
                tv1.setGravity(Gravity.CENTER);
                row.addView(tv1);
                TextView tv2 = new TextView(getBaseContext());
                tv2.setText("Total Spent");
                tv2.setTextColor(getResources().getColor(R.color.statusBarWhite));
                tv2.setPadding(10,10,10,10);
                tv2.setGravity(Gravity.CENTER);
                row.addView(tv2);
                teams.addView(row);
                for(int i=0;i<documentSnapshots.size();i++)
                {
                    TableRow _row = new TableRow(getBaseContext());
                    TextView _tv = new TextView(getBaseContext());
                    _tv.setText(String.valueOf(i+1));
                    _tv.setTextColor(getResources().getColor(R.color.statusBarWhite));
                    _tv.setPadding(10,10,10,10);
                    _tv.setGravity(Gravity.CENTER);
                    _row.addView(_tv);
                    TextView _tv0 = new TextView(getBaseContext());
                    _tv0.setText(documentSnapshots.get(i).getId().toString());
                    _tv0.setTextColor(getResources().getColor(R.color.statusBarWhite));
                    _tv0.setPadding(10,10,10,10);
                    _tv0.setGravity(Gravity.CENTER);
                    _row.addView(_tv0);
                    TextView _tv1 = new TextView(getBaseContext());
                    _tv1.setText(documentSnapshots.get(i).contains("totalPlayers")?documentSnapshots.get(i).get("totalPlayers").toString():"0");
                    _tv1.setTextColor(getResources().getColor(R.color.statusBarWhite));
                    _tv1.setPadding(10,10,10,10);
                    _tv1.setGravity(Gravity.CENTER);
                    _row.addView(_tv1);
                    TextView _tv2 = new TextView(getBaseContext());
                    _tv2.setText(documentSnapshots.get(i).contains("totalMoney")?documentSnapshots.get(i).get("totalMoney").toString():"0");
                    _tv2.setTextColor(getResources().getColor(R.color.statusBarWhite));
                    _tv2.setPadding(10,10,10,10);
                    _tv2.setGravity(Gravity.CENTER);
                    _row.addView(_tv2);
                    final int finalI = i;
                    _row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(),AuctionPlayers.class);
                            intent.putExtra("teamName",documentSnapshots.get(finalI).getId());
                            startActivity(intent);
                        }
                    });
                    teams.addView(_row);
                }
            }
        });
    }
}