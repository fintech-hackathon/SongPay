package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private String[] date,remain,sub;
    HistoryHolder historyHolder;

    public HistoryAdapter(String[] date, String[] remain,String[] sub){
        this.date = date;
        this.remain = remain;
        this.sub = sub;
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder{
        public TextView dateTextView,remainTextView,subTextView;

        public HistoryHolder(View view){
            super(view);
            this.dateTextView = view.findViewById(R.id.dateText);
            this.remainTextView = view.findViewById(R.id.remainText);
            this.subTextView = view.findViewById(R.id.subText);
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
        holder.dateTextView.setText(this.date[position]);
        holder.remainTextView.setText(this.remain[position]);
        holder.subTextView.setText(this.sub[position]);
    }

    @Override
    public int getItemCount() {
        return date.length;
    }
}


