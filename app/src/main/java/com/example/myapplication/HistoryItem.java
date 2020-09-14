package com.example.myapplication;

public class HistoryItem {
    String date,remain,sub;

    public HistoryItem(String date, String remain, String sub){
        this.date = date;
        this.remain = remain;
        this.sub = sub;
    }

    public String getDate() {
        return date;
    }

    public String getRemain() {
        return remain;
    }

    public String getSub() {
        return sub;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
