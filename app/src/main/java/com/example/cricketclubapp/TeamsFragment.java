package com.example.cricketclubapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<Team> listTeam;
    TeamRecyclerAdapter recyclerViewAdapter;
    public TeamsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.teams, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.teamsRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new TeamRecyclerAdapter(getContext(), listTeam);
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTeam = new ArrayList<>();
        listTeam.add(new Team("Chennai Super Kings", 100000, 22));
        listTeam.add(new Team("Punjab Kings", 200000, 21));
        listTeam.add(new Team("Rajasthan Royals", 100000, 20));
        listTeam.add(new Team("Kolkata Knight Riders", 300000, 25));
        listTeam.add(new Team("Mumbai Indians", 100000, 22));
        listTeam.add(new Team("Royal Chalengers Banglore", 150000, 24));


    }
}
