package com.example.lorcan.palo.SQLite;

public class SQLiteData {
    private String id;
    private int clicks;


    public SQLiteData(String id){
        this.id = id;
    }

    public SQLiteData(String id, int clicks) {
        this.id = id;
        this.clicks = clicks;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    @Override
    public String toString() {
        String output = clicks + " x " + id;

        return output;
    }
}
