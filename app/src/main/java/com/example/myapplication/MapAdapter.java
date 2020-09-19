package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapHolder> {
    private String[] title,address;
    MapHolder mapHolder;
    String result = null;
    String url = "http://115.85.180.70:3001/owner/allowner";

    JSONObject temp = new JSONObject();


    public MapAdapter(String[] title, String[] address) throws ExecutionException, InterruptedException, JSONException {
        this.title = title;
        this.address = address;

        NetworkTask parser = new NetworkTask(url, temp, "POST");
        Log.i("msg","=========");
        result = parser.execute().get();

        JSONArray array = new JSONArray(result);

        for(int i=0; i<array.length(); i++){
            JSONObject obj =  (JSONObject) array.get(i);
            Log.i("msg", obj.toString());

            title[i] = obj.get("o_singingroomname").toString();
            address[i]=obj.get("o_address").toString();

        }

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

        // 리스트뷰 아이템 클릭시, 예약화면(reserveActivity)로 전환.
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
