package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapHolder> {
    private ArrayList<String> title = new ArrayList();
    private ArrayList<String> address = new ArrayList();
    private String[] songbymoney;
    private String[] roomnum;
    private String[] o_id;
    MapHolder mapHolder;
    String result = null;
    String url = "http://115.85.180.70:3001/owner/allowner";

    JSONObject temp = new JSONObject();


    public MapAdapter(ArrayList<String> title, ArrayList<String> address) throws ExecutionException, InterruptedException, JSONException {
        this.title = title;
        this.address = address;
//        this.songbymoney=songbymoney;
//        this.roomnum=roomnum;
//        this.o_id=o_id;


        NetworkTask parser = new NetworkTask(url, temp, "POST");
        Log.i("msg","=========");
        result = parser.execute().get();

        JSONArray array = new JSONArray(result);

        for(int i=0; i<array.length(); i++){
            JSONObject obj =  (JSONObject) array.get(i);
            Log.i("msg", i+obj.toString());

            title.add(obj.get("o_singingroomname").toString());
            address.add(obj.get("o_address").toString());
//            songbymoney[i]=obj.get("o_songByMoney").toString();
//            roomnum[i]=obj.get("roomnum").toString();
//            o_id[i]=obj.get("oid").toString();


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
        holder.titleTextView.setText(this.title.get(position));
        holder.subTextView.setText(this.address.get(position));
        Log.i("msg",""+position+title.get(position));

        // 리스트뷰 아이템 클릭시, 예약화면(reserveActivity)로 전환.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context1 = view.getContext();
                Context context2 = view.getContext();
                Intent reserveIntent = new Intent(context1,ReserveActivity.class);
                Intent findIntent = new Intent(context2, FindActivity.class);
                reserveIntent.putExtra("title", title.get(position));
                reserveIntent.putExtra("address", address.get(position));
                findIntent.putExtra("title", title.get(position));
                findIntent.putExtra("address", address.get(position));

//                reserveIntent.putExtra("songbymoney",songbymoney[position]);
//                reserveIntent.putExtra("roomnum",roomnum[position]);
//                reserveIntent.putExtra("o_id",o_id[position]);
                context1.startActivity(reserveIntent);
                context2.startActivity(findIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }
}
