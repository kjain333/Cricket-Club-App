package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class ChooseDate extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        final Intent intent = getIntent();
        final Integer type = intent.getIntExtra("type", 0);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                if(type == 1){
                    Intent intent = new Intent(getApplicationContext(), DateAttendance.class);
                    String date = i2 + "/" + (i1+1) + "/" + i;
                    intent.putExtra("date", date);
                    startActivity(intent);
                }else if (type == 2) {
                    Intent intent = new Intent(getApplicationContext(), FullNetStats.class);
                    String date = i + "" + (i1+1) + "" +  i2;
                    if(i1 < 9){
                        date = i + "0" + (i1+1) + "" +  i2;
                    }
                    intent.putExtra("date", date);
                    startActivity(intent);
                }else if(type == 3){
                    Intent intent = new Intent(getApplicationContext(), BowlerNetStats.class);
                    String date = i + "" + (i1+1) + "" +  i2;
                    if(i1 < 9){
                        date =  i + "0" + (i1+1) + "" +  i2;
                    }
                    intent.putExtra("date", date);
                    startActivity(intent);
                }

            }
        });

    }


}