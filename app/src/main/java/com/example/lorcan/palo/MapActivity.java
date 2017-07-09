package com.example.lorcan.palo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapActivity extends FragmentActivity implements LocationListener {

    private MapView mapView;
    public LatLng currLocation;
    private LocationManager locationManager;
    //private LocationListener locationListener;
    Button positionButton;
    MapboxMap mapBoxGlobal;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();


        getLocFromDB getLocation = new getLocFromDB(this);
        getLocation.getLocation();

        Mapbox.getInstance(this, "pk.eyJ1IjoicGFsb2hodSIsImEiOiJjajQ4cDdmb2MwZjgyMnFxb2hrMnludTJkIn0.Giac8tbDSxxOu_ivc2PaUg");
        setContentView(R.layout.activity_map);
        positionButton = (Button) findViewById(R.id.positionButton);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //checks for permission.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                mapBoxGlobal = mapboxMap;

                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked();
                    }
                });

            }
        });

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



    public void buttonClicked() {
        //LocationManager gets the Location Service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //what to do if permission is denied
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        CameraPosition position = new CameraPosition.Builder()
                .target(currLocation) // Sets the new camera position
                .zoom(16) // Sets the zoom
                .bearing(359) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder
        IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
        Icon icon = iconFactory.fromResource(R.drawable.positionIcon);

        mapBoxGlobal.addMarker(new MarkerViewOptions().position(currLocation).icon(icon));
        mapBoxGlobal.animateCamera(CameraUpdateFactory.newCameraPosition(position), 7000);
        mapBoxGlobal.getTrackingSettings();
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
