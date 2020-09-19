package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private int[] image;
    private ArrayList<Object> title, singer, youtube_url,views,likes;

    MusicHolder musicHolder;

    public MusicAdapter(int[] image) {
        this.image = image;

        HashMap<String,ArrayList<Object>> hm = getList();


        this.title = hm.get("title");
        this.singer = hm.get("singer");
        this.youtube_url = hm.get("youtube_url");
        this.views = hm.get("view");
        this.likes = hm.get("likes");
    }


    public static class MusicHolder extends RecyclerView.ViewHolder {
        public ImageView musicImageView;
        public TextView songTitleTextView, singerTextView, viewTextView, recommendTextView;

        public MusicHolder(View view) {
            super(view);
            this.musicImageView = view.findViewById(R.id.musicImageView);
            this.songTitleTextView = view.findViewById(R.id.songTitleTextView);
            this.singerTextView = view.findViewById(R.id.singerTextView);
            this.viewTextView = view.findViewById(R.id.viewTextview);
            this.recommendTextView = view.findViewById(R.id.recommendTextView);
        }
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);

        musicHolder = new MusicHolder(holderView);
        return musicHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {

        holder.songTitleTextView.setText(title.get(position).toString());
        holder.singerTextView.setText(singer.get(position).toString());
        holder.recommendTextView.setText(likes.get(position).toString());
        holder.viewTextView.setText(views.get(position).toString());
        holder.musicImageView.setImageResource(image[position]);

        // 리스트뷰 아이템 클릭시, 음악 재생 화면(MusicPlayerActivity.java)로 전환.
        // TODO : 해당 아이템 youtube 읽어올 데이터 정의해서, playerIntent 랑 같이 전환하면 되겠습니다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 여기에 조회수 증가하는 로직 짜면 되겠습니다.
                Context context = view.getContext();
                Intent playerIntent = new Intent(context, MusicPlayerActivity.class);
                playerIntent.putExtra("title", title.get(position).toString());
                playerIntent.putExtra("singer", singer.get(position).toString());
                playerIntent.putExtra("link", youtube_url.get(position).toString());

                uphites(youtube_url.get(position).toString());

                context.startActivity(playerIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    void uphites(String link){

        String url = "http://115.85.180.70:3001/record/uphites";
        JSONObject object = new JSONObject();

        try{
            object.put("r_url",link);
        }
        catch (Exception e){
            Log.e("error",e.getMessage());
        }

        NetworkTask networkTask = new NetworkTask(url, object,"POST");
        networkTask.execute();
    }

    HashMap<String,ArrayList<Object>> getList(){
        HashMap<String, ArrayList<Object>> hm = new HashMap<>();
        String url = "http://115.85.180.70:3001/record/getList";

        JSONObject object = new JSONObject();

        ArrayList<Object> title = new ArrayList<>();
        ArrayList<Object> singer = new ArrayList<>();
        ArrayList<Object> youtube_url = new ArrayList<>();
        ArrayList<Object> view = new ArrayList<>();
        ArrayList<Object> likes = new ArrayList<>();

        try {
            NetworkTask parser = new NetworkTask(url, object, "POST");
            String result = parser.execute().get();
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = (JSONObject) array.get(i);
                title.add(temp.get("r_title"));
                singer.add(temp.get("r_name"));
                youtube_url.add(temp.get("r_url"));
                view.add(temp.get("r_views"));
                likes.add(temp.get("r_likes"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        hm.put("title",title);
        hm.put("singer",singer);
        hm.put("youtube_url",youtube_url);
        hm.put("view",view);
        hm.put("likes",likes);

        return hm;
    }
}

