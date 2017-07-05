package com.example.lorcan.palo;

import com.google.android.gms.maps.model.LatLng;

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
    SendLocToDB send = new SendLocToDB(getApplicationContext());

    public User(String email, String password, Double lat, Double lng, Boolean isOnline, String status){
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.isOnline = isOnline;
        this.status = status;
    }

    public User(){

    }



    // --- setter ---
    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLat(Double lat){
        this.lat = lat;
    }

    public void setLng(Double lng){
        this.lng = lng;
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

    private void updateDB() {

        send.sendLocation(this.email, this.lat, this.lng); // later insert correct email of user
    }

}
