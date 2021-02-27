package com.example.cricketclubapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<Team> mData;

    public TeamRecyclerAdapter(Context mContext, List<Team> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public TeamRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.team_item, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TeamRecyclerAdapter.MyViewHolder holder, int position) {

        holder.teamName.setText(mData.get(position).getTeamName());
        holder.teamBudget.setText(String.valueOf(mData.get(position).getRemBudget()));
        holder.teamSize.setText(String.valueOf(mData.get(position).getTeamSize()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void updateAdapter(List<Team> mDataList) {
        mData = mDataList;
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView teamName, teamBudget, teamSize;


        public MyViewHolder(View itemView){
            super(itemView);

            teamName = (TextView) itemView.findViewById(R.id.teamName);
            teamBudget = (TextView) itemView.findViewById(R.id.remBudget);
            teamSize = (TextView) itemView.findViewById(R.id.squadSize);

        }
    }
}
