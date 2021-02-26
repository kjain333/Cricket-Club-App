package com.example.cricketclubapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayersFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<Player> listPlayer;


    public PlayersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.players, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.playersRecyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listPlayer);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listPlayer = new ArrayList<>();
        listPlayer.add(new Player("Sachit Kalia", "Brahmaputra", "B.Tech", R.drawable.profile, true, 6000, "CSK"));
        listPlayer.add(new Player("Rushabh Parikh", "Siang", "B.Tech", R.drawable.profile, true, 5000, "MI"));
        listPlayer.add(new Player("Sachit Kalia", "Brahmaputra", "B.Tech", R.drawable.profile, true, 6000, "CSK"));
        listPlayer.add(new Player("Rushabh Parikh", "Siang", "B.Tech", R.drawable.profile, true, 5000, "MI"));
        listPlayer.add(new Player("Sachit Kalia", "Brahmaputra", "B.Tech", R.drawable.profile, true, 6000, "CSK"));
        listPlayer.add(new Player("Rushabh Parikh", "Siang", "B.Tech", R.drawable.profile, true, 5000, "MI"));
        listPlayer.add(new Player("Sachit Kalia", "Brahmaputra", "B.Tech", R.drawable.profile, true, 6000, "CSK"));
        listPlayer.add(new Player("Rushabh Parikh", "Siang", "B.Tech", R.drawable.profile, true, 5000, "MI"));
        listPlayer.add(new Player("Sachit Kalia", "Brahmaputra", "B.Tech", R.drawable.profile, true, 6000, "CSK"));
        listPlayer.add(new Player("Rushabh Parikh", "Siang", "B.Tech", R.drawable.profile, false, 0, ""));


    }


}
