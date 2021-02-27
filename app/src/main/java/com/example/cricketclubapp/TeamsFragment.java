package com.example.cricketclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

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
        FirebaseFirestore.getInstance().collection("auctionTeams").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                listTeam = new ArrayList<>();
                for(int i=0;i<documentSnapshots.size();i++)
                {
                    if(documentSnapshots.get(i).contains("budget"))
                    listTeam.add(new Team(documentSnapshots.get(i).getId(),Integer.parseInt(documentSnapshots.get(i).get("budget").toString())-Integer.parseInt(documentSnapshots.get(i).get("totalMoney").toString()),Integer.parseInt(documentSnapshots.get(i).get("totalPlayers").toString())));
                }
                recyclerViewAdapter.updateAdapter(listTeam);
            }
        });

    }
}
