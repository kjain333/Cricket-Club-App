package com.example.cricketclubapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomArrayAdapter extends ArrayAdapter<Member> {

    Context mContext;
    int mResource;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Member> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String points = getItem(position).getPoints();
        String image = getItem(position).getImage();
        Integer pos = position+1;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView numView = (TextView) convertView.findViewById(R.id.pointsTextView1);
        TextView nameView = (TextView) convertView.findViewById(R.id.pointsTextView2);
        TextView pointView = (TextView) convertView.findViewById(R.id.pointsTextView3);
        CircleImageView imageView = convertView.findViewById(R.id.ptImage);

        numView.setText(pos.toString());
        nameView.setText(name);
        pointView.setText(points);
        if(image.equals("default")){
            Picasso.get().load(R.drawable.profile).into(imageView);
        }else{
            Picasso.get().load(image).into(imageView);
        }
        return convertView;
    }
}
