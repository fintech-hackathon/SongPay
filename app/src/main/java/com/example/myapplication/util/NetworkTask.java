package com.example.myapplication.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;


public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject values;
    private String method;

    public NetworkTask(String url, JSONObject values, String method) {

        this.url = url;
        this.values = values;
        this.method = method;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values,method); // 해당 URL로 부터 결과물을 얻어온다.

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
//        Log.i("msg",s);
    }
}
