package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Stats extends AppCompatActivity {

    Button fullNetBatting, fullNetBowling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        fullNetBatting = (Button) findViewById(R.id.fullNetBatting);
        fullNetBowling = (Button) findViewById(R.id.fullNetBowling);

    }

    public void getBattingStats(View view){
        Intent intent = new Intent(getApplicationContext(), ChooseDate.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    public void getBowlingStats(View view){
        Intent intent = new Intent(getApplicationContext(), ChooseDate.class);
        intent.putExtra("type", 3);
        startActivity(intent);
    }
}