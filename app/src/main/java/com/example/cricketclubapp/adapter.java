package com.example.cricketclubapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class adapter extends PagerAdapter {

    private List<Model> drillModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public adapter(List<Model> drillModels, Context context) {
        this.drillModels = drillModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return drillModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.drill, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.drillImage);
        title = view.findViewById(R.id.drillTitle);
        desc = view.findViewById(R.id.drillDesc);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FitnessDrillActivity.class);
                context.startActivity(intent);
            }
        });


        imageView.setImageResource(drillModels.get(position).getImage());
        title.setText(drillModels.get(position).getTitle());
        desc.setText(drillModels.get(position).getDesc());
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

}
