package com.example.cricketclubapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Player> mData;
    public RecyclerViewAdapter(Context mContext, List<Player> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.player_item, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.tvName.setText(mData.get(position).getName());
        holder.tvHostel.setText(mData.get(position).getHostel());
        holder.tvProgramme.setText(mData.get(position).getProgramme());
        if(mData.get(position).getPhoto().contains("All-Rounder"))
        {
            holder.img.setImageResource(R.drawable.team);
        }
        else if(mData.get(position).getPhoto().contains("Batsman"))
        {
            holder.img.setImageResource(R.drawable.batsman);
        }
        else
        {
            holder.img.setImageResource(R.drawable.ball1);
        }
        holder.tvAmount.setText(String.valueOf(mData.get(position).getAmount()) + " INR");
        boolean isSold = true;
        if(mData.get(position).getAmount() == 0){
            isSold = false;
        }
        if(isSold){
            holder.tvStatus.setText("SOLD TO " + mData.get(position).getTeam());
        }else{
            holder.tvStatus.setText("UNSOLD");
            holder.tvAmount.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void updateAdapter(List<Player> mDataList) {
        mData = mDataList;
        Collections.sort(mData,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Player p1 = (Player) o1;
                Player p2 = (Player) o2;
                return  String.valueOf(p2.getTime()).compareTo(String.valueOf(p1.getTime()));
            }
        });
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvHostel, tvProgramme, tvStatus, tvAmount;
        private ImageView img;

        public MyViewHolder(View itemView){
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.playerName);
            tvHostel = (TextView) itemView.findViewById(R.id.playerHostel);
            tvProgramme = (TextView) itemView.findViewById(R.id.playerProgramme);
            tvStatus = (TextView) itemView.findViewById(R.id.sellingStatus);
            tvAmount = (TextView) itemView.findViewById(R.id.sellingAmount);
            img = (ImageView) itemView.findViewById(R.id.imagePlayer);

        }
    }
}
