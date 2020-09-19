package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapHolder> {
    private ArrayList<String> title,address,room,songbymondy,owner;
    MapHolder mapHolder;
    String result = null;
    Context context;
    String ID;

    public MapAdapter(ArrayList<String> title, ArrayList<String> address,ArrayList<String> room,ArrayList<String> songbymondy,ArrayList<String> owner,Context context) {
        this.title = title;
        this.address = address;
        this.room = room;
        this.songbymondy = songbymondy;
        this.owner = owner;
        this.context = context;

        SharedPreferences sharedPreferences= context.getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        ID = sharedPreferences.getString("Id","default Name");  // 불러올려는 key, default Value
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
    public void onBindViewHolder(@NonNull MapHolder holder, final int position) {
        holder.titleTextView.setText(this.title.get(position));
        holder.subTextView.setText(this.address.get(position));

        // 리스트뷰 아이템 클릭시, 예약화면(reserveActivity)로 전환.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkReserv(ID)){
                    Context context = view.getContext();
                    Intent reserveIntent = new Intent(context,ReserveActivity.class);
                    reserveIntent.putExtra("title",title.get(position));
                    reserveIntent.putExtra("address",address.get(position));
                    reserveIntent.putExtra("room",room.get(position));
                    reserveIntent.putExtra("songbymoney",songbymondy.get(position));
                    reserveIntent.putExtra("owner",owner.get(position));
                    context.startActivity(reserveIntent);
                }
                else{
                    Toast.makeText(context.getApplicationContext(), "이미 예약하셨습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    boolean checkReserv(String id){
        Log.i("msg","id : "+id);
        boolean flag = true;
        String url = "http://115.85.180.70:3001/room/getListByUser";

        JSONObject object = new JSONObject();

        try {
            object.put("sr_u_id",id);
            NetworkTask parser = new NetworkTask(url, object, "POST");
            String result = parser.execute().get();
            if(!result.isEmpty()){
                flag = false;
            }
        }
        catch (Exception e){
        }
        Log.i("msg","flag : "+flag);
        return flag;
    }

}
