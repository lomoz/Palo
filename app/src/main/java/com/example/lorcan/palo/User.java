package com.example.lorcan.palo;


//import com.google.android.gms.maps.model.LatLng;


public class User {
    private String email;
    //private String password;
    private String name;
    //private Double lat;
    //private Double lng;
    //private Boolean isOnline;
    private String status;

    /*
    public User(String email, String password, Double lat, Double lng, Boolean isOnline, String status, String name){
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.isOnline = isOnline;
        this.status = status;
        this.name = name;
    }
*/
    User(){
        //SendLocToDB send = new SendLocToDB();
    }



    // --- setter ---
    
    public void setEmail(String email){
        this.email = email;
    }
/*
    public void setPassword(String password){
        this.password = password;
    }

    public void setLocation(LatLng location){
        try {
            this.lat = location.latitude;
            this.lng = location.longitude;
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setIsOnline(Boolean isOnline){
        this.isOnline = isOnline;
    }
*/
    public void setStatus(String status){
        this.status = status;
    }

    public void setName(String name){this.name = name;}

    // --- getter ---

    public String getEmail(){
        return this.email;
    }
/*
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
    }*/

    public String getStatus(){
        return this.status;
    }

    public String getName(){
        return this.name;
    }
/*
    public void updateLocation() {

        try {
            send.sendLocation(this.email, this.lat, this.lng);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }*/

}
