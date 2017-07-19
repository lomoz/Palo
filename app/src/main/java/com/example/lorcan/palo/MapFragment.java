package com.example.lorcan.palo;


import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

// test thinkpad
    GoogleMap map;
    public LatLng currLocation = new LatLng(51.6, 6.2);
    private LocationManager locationManager;
    Button positionButton;
    User user;
    ArrayList arrayListOtherUsers;
    MarkerOptions markerOptions;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.markerOptions = new MarkerOptions()
                .position(this.currLocation)
                .title("Status?");
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        user = new User();
        arrayListOtherUsers = MainActivity.arrayListOtherUsers;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        map.setMinZoomPreference(14);


        Toast.makeText(getContext(), "Current Location: " + currLocation, Toast.LENGTH_SHORT).show();

        markerOptions.position(this.currLocation).title("Status?");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();

        for(int i = 0; i< arrayListOtherUsers.size(); i++){

            String[] array = (String[]) arrayListOtherUsers.get(i);
            LatLng locationOther = new LatLng(Double.parseDouble(array[1]), Double.parseDouble(array[2]));

            //---- getting distance between user location-----
            float[] results = new float[1];
            Location.distanceBetween(currLocation.latitude, currLocation.longitude, locationOther.latitude, locationOther.longitude, results);
            float distance = results[0];
            //---------------------------

            if(distance<100){ // not ready yet!!! at this point we also need to check "isOnline" and we need to get the Status
                LatLng position = locationOther;
                MarkerOptions marker1 = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
                marker1.position(position);
                map.addMarker(marker1);
            }

        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
    }


    public void buttonClicked(){

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();


    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(), "Current Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();

        this.currLocation = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(this.currLocation).title("Status?");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

}