package com.example.cricketclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class DrillsActivity extends AppCompatActivity {

    ViewPager viewPager;
    adapter drillAdapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drills);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.batsman1, "Batting Drills", "Particular shot drill using synthetic balls."));
        models.add(new Model(R.drawable.ball, "Bowling Drills", "Hit the target (use a cone)"));
        models.add(new Model(R.drawable.fielding, "Fielding Drills", "Catching and ground-fielding"));
        models.add(new Model(R.drawable.sprint, "Fitness Drills", "Sprint competition among all club members having several knockout rounds"));

        drillAdapter = new adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(drillAdapter);
        viewPager.setPadding(130, 0 , 130, 0);
        viewPager.setOffscreenPageLimit(4);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (drillAdapter.getCount()-1) && position < (colors.length -1)){
                   viewPager.setBackgroundColor(
                           (Integer) argbEvaluator.evaluate(
                                   positionOffset,
                                   colors[position],
                                   colors[position+1]));
                }else{
                  viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}