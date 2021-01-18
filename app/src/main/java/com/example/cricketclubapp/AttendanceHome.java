package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AttendanceHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_home);
    }

    public void take(View view){
        Intent intent = new Intent(getApplicationContext(), Attendance.class);
        startActivity(intent);
    }

    public void getDatewise(View view){
        Intent intent = new Intent(getApplicationContext(), ChooseDate.class);
        startActivity(intent);

    }

    public void getPersonal(View view){
        Intent intent = new Intent(getApplicationContext(), PersonalAttendance.class);
        startActivity(intent);
    }
}