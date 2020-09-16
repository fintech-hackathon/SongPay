package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapHolder> {
    private String[] title,address;
    MapHolder mapHolder;

    public MapAdapter(String[] title, String[] address){
        this.title = title;
        this.address = address;
    }

    public static class MapHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView,subTextView;

        public MapHolder(View view){
            super(view);
            this.titleTextView = view.findViewById(R.id.titleTextView);
            this.subTextView = view.findViewById(R.id.subTextView);
        }
    }

    @NonNull
    @Override
    public MapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);

        mapHolder = new MapHolder(holderView);
        return mapHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapAdapter.MapHolder holder, final int position) {
        holder.titleTextView.setText(this.title[position]);
        holder.subTextView.setText(this.address[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent reserveIntent = new Intent(context,ReserveActivity.class);
                reserveIntent.putExtra("title",title[position]);
                reserveIntent.putExtra("address",address[position]);
                context.startActivity(reserveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
