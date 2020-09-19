package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private ArrayList<String> date,sub;

    HistoryHolder historyHolder;

    public HistoryAdapter(ArrayList<String> date,ArrayList<String> sub){
        this.date = date;
        this.sub = sub;
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView, priceTextView, subTextView;

        public HistoryHolder(View view) {
            super(view);
            this.dateTextView = view.findViewById(R.id.dateText);
            this.subTextView = view.findViewById(R.id.subText);
            this.priceTextView = view.findViewById(R.id.priceText);
        }
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_item, parent, false);

        historyHolder = new HistoryHolder(holderView);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
//        String ex = "충전/+1000";
        holder.dateTextView.setText(this.date.get(position));
        holder.subTextView.setText(this.sub.get(position).split("/")[0]);
        holder.priceTextView.setText(this.sub.get(position).split("/")[1]);
    }

    @Override
    public int getItemCount() {
        return date.size();
    }
}


