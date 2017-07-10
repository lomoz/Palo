package com.example.lorcan.palo;

import android.content.Context;
import android.support.v4.app.FragmentActivity;


import com.mapbox.mapboxsdk.geometry.LatLng;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * Created by paul on 05.07.17.
 */

public class User {
    private String email;
    private String password;
    private Double lat;
    private Double lng;
    private Boolean isOnline;
    private String status;
    private SendLocToDB send;

    public User(String email, String password, Double lat, Double lng, Boolean isOnline, String status){
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.isOnline = isOnline;
        this.status = status;
    }

    public User(FragmentActivity activity){
        this.send = new SendLocToDB();
    }



    // --- setter ---
    
    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLocation(LatLng location){
        try {
            this.lat = location.getLatitude();
            this.lng = location.getLongitude();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setIsOnline(Boolean isOnline){
        this.isOnline = isOnline;
    }

    public void setStatus(String status){
        this.status = status;
    }


    // --- getter ---

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public Double getLat(){
        return this.lat;
    }

    public Double getLng(){
        return this.lng;
    }

    public Boolean getIsOnline(){
        return this.isOnline;
    }

    public String getStatus(){
        return this.status;
    }

    public void updateDB() {

        try {
            send.sendLocation(this.email, this.lat, this.lng);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

}

/* TODO:
Update the Database with a column for isOnline and status. Maybe a new Table?
*/