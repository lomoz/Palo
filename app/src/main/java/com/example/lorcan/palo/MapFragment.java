package com.example.lorcan.palo;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {


    GoogleMap map;
    public LatLng currLocation;
    private LocationManager locationManager;
    Button positionButton;
    User user;
    HashMap<String, LatLng> hashMapOtherUsers = new HashMap<>();

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        user = new User();



        hashMapOtherUsers = MainActivity.locationsFromDB.getData();



        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //checks for permission.
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return view;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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

        //checks for permission.
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        map = googleMap;

        MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.location));

        LatLng ersatzLatLng = new LatLng(51.192429,6.793681);
        marker.position(ersatzLatLng).title("Düsseldorf");
        map.addMarker(marker);
        map.moveCamera(CameraUpdateFactory.newLatLng(ersatzLatLng));
        map.setMinZoomPreference(14);

        System.out.println("__________________________________________________" + hashMapOtherUsers);
        for(int i = 0; i<hashMapOtherUsers.size(); i++){
            LatLng position = hashMapOtherUsers.get("palo.hhu@gmail.com"); // is only a example -> before getting latlng we have to looking for distanceTo (default Radius?) so we will get a List with Emails(Primary Key) and can use them to get position near the user
            MarkerOptions marker1 = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.locations));
            marker1.position(position);
            map.addMarker(marker1);
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
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //what to do if permission is denied
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        // location Manager requesting the new position
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);


        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(currLocation);

        user.updateDB();


    }

    @Override
    public void onLocationChanged(Location location) {
        //set current Location
        currLocation = new LatLng(location.getLatitude(), location.getLongitude());

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(currLocation);

        user.updateDB();
    }

    @Override
    public void onProviderDisabled(String provider) {
        //starts Settings Activity
        Intent gpsOptionsIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);

    }

    @Override
    public void onProviderEnabled(String provider) {

        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

}