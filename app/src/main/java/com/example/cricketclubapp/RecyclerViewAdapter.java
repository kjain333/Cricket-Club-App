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

import java.util.List;

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
        holder.img.setImageResource(mData.get(position).getPhoto());
        holder.tvAmount.setText(String.valueOf(mData.get(position).getAmount()) + " INR");
        boolean isSold = mData.get(position).isSold();
        if(isSold){
            holder.tvStatus.setText("SOLD TO " + mData.get(position).getTeam());
        }else{
            holder.tvStatus.setText("UNSOLD");
            holder.tvStatus.setBackgroundColor(R.color.colorPrimary);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void updateAdapter(List<Player> mDataList) {
        mData = mDataList;
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
