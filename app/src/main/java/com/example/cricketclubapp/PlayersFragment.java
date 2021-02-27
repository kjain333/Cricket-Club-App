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

public class PlayersFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<Player> listPlayer;
    RecyclerViewAdapter recyclerViewAdapter;
    public PlayersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.players, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.playersRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listPlayer);
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPlayer = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("auctionPlayers").orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                listPlayer = new ArrayList<>();
                final List<String> playerSold = new ArrayList<>();
                for(int i=0;i<documentSnapshots.size();i++)
                {
                    Log.d("debug",String.valueOf(i));
                    final int finalI = i;
                    FirebaseFirestore.getInstance().collection("users").document(documentSnapshots.get(i).get("playerId").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d("debug",documentSnapshot.get("hostel").toString());
                            playerSold.add(documentSnapshot.getId());
                            listPlayer.add(new Player(documentSnapshots.get(finalI).get("playerName").toString(),documentSnapshot.get("hostel").toString(),documentSnapshot.get("programme").toString(),R.drawable.profile,true,Integer.parseInt(documentSnapshots.get(finalI).get("price").toString()),documentSnapshots.get(finalI).get("team").toString()));
                            recyclerViewAdapter.updateAdapter(listPlayer);
                            Log.d("debug","done");
                        }
                    });
                }
//                FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot documentSnapshot: snapshotList)
//                        {
//                            if(!playerSold.contains(documentSnapshot.getId()))
//                            {
//                                listPlayer.add(new Player(documentSnapshot.get("username").toString(),documentSnapshot.get("hostel").toString(),documentSnapshot.get("programme").toString(),R.drawable.profile,false,0,""));
//                            }
//                            recyclerViewAdapter.updateAdapter(listPlayer);
//                        }
//                    }
//                });
            }
        });
    }
}
