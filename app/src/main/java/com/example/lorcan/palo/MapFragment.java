package com.example.lorcan.palo;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    GoogleMap map;
    public LatLng currLocation = new LatLng(53.2, 6.2);
    private LocationManager locationManager;
    Button positionButton;
    User user;
    ArrayList arrayListOtherUsers;
    MarkerOptions markerOptions;
    String lat;
    String lng;
    String status;
    String studyCourse;
    Bundle bundle;
    Bundle bundleLocation;
    Bundle bundleColor;
    Bundle bundleCurrLoc;
    Float markerColorFloat;
    ArrayList<String> args = new ArrayList<>();

    Button btnChangeInMap;

    String currentTime;
    View view;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_map, container, false);
        // set individual Controls and Gestures for the Google Map
        GoogleMapOptions options = new GoogleMapOptions();
        //options.compassEnabled(true);
        options.mapToolbarEnabled(false);

        markerOptions = new MarkerOptions()
                .position(currLocation);
        user = new User();
        MainActivity main = new MainActivity();
        main.getData();
        arrayListOtherUsers = MainActivity.arrayListOtherUsers;


        // Get the data from ProfileFragment.java here
        bundle = getArguments();

        if (bundle != null) {
            status = bundle.getString("status");
            studyCourse = bundle.getString("study course");
            if(bundle.getStringArrayList("args") != null){
                args = bundle.getStringArrayList("args");
            }
        }

        // Get the data from SettingsFragment.java here
        bundleColor = getArguments();

        if ((((bundleColor != null
                && bundleColor.getStringArrayList("args") == null)
                && bundleColor.getString("status") == null)
                && bundleColor.getString("study course") == null)) {
            markerColorFloat = bundleColor.getFloat("markerColor");
        }
        else {
            // default marker color
            markerColorFloat = BitmapDescriptorFactory.HUE_RED;
        }

        /*
        bundleLocation = getArguments();

        if (bundleLocation != null) {
            currLocation = new LatLng(bundleLocation.getDouble("latitude"), bundleLocation.getDouble("longitude"));
            System.out.println("######################" + currLocation + "####################");
        }
        */



        final EditText etStatusInMap = (EditText) view.findViewById(R.id.etStatusInMap);

        btnChangeInMap = (Button)view.findViewById(R.id.btnChangeInMap);
        btnChangeInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.btnChangeClicked();
                */

                etStatusInMap.getText();
            }
        });

        return view;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final FloatingActionButton fabUpdate = (FloatingActionButton) view.findViewById(R.id.fabUpdate);
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMap();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // set individual Controls and Gestures for the Google Map
        GoogleMapOptions options = new GoogleMapOptions();
        //options.compassEnabled(true);
        options.mapToolbarEnabled(false);

        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }


        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
       // map.setMinZoomPreference(14);
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        if ((args == null || args.size() == 0) && bundle != null){
            MarkerOptions markerOptionsOwnStatus = new MarkerOptions()
                    .position(currLocation)
                    .title("Username" + " | " + currentTime)
                    .snippet(status);
            map.addMarker(markerOptionsOwnStatus);
        }
        else {
            //only if Bundle is an ArrayList

            for (int i = 0; i < args.size(); i = i + 5) {
                System.out.println("DIE ZEIT: "+args.get(i+3));
                MarkerOptions markerOptions1 = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(args.get(i + 1)), Double.parseDouble(args.get(i + 2))))
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColorFloat))
                        .title("Username" + " | " + args.get(i+3))
                        .snippet(args.get(i));
                map.addMarker(markerOptions1);
            }
        }

        //------------------------------- 


        Toast.makeText(getContext(), getString(R.string.current_location) + " " + currLocation, Toast.LENGTH_LONG).show();

        //this marker is always placed, regardless of user location. so it's better to not put it.
        /*
        markerOptions.position(this.currLocation).title(status);
        map.addMarker(markerOptions);
        */
        map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();

        /*
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
        */

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

    public void updateMap() {
        UpdateMapFragment upFragment = new UpdateMapFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        upFragment,
                        upFragment.getTag()
                ).commit();

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();


    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(getContext(), "Current Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();

        this.currLocation = new LatLng(location.getLatitude(), location.getLongitude());

        // set individual Controls and Gestures for the Google Map
        GoogleMapOptions options = new GoogleMapOptions();
        options.zoomControlsEnabled(false);
        options.compassEnabled(true);
        options.mapToolbarEnabled(false);

        //this marker is placed at the updated current user location
        markerOptions.position(this.currLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title("Username" + " | " + currentTime)
                .snippet("this is the status");

        if (currLocation != null) {
            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));
        }
        else {
            Toast.makeText(getContext(), "no current location", Toast.LENGTH_SHORT).show();
        }

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