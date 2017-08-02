package com.example.lorcan.palo;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LoadDataActivity.LocationCallback {


    public static final String TAG = MapFragment.class.getSimpleName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LoadDataActivity mLoadDataActivity;

    GoogleMap map;
    public LatLng currLocation = new LatLng(51.6, 6.2);

    Button positionButton;
    User user;
    MarkerOptions markerOptions;
    String status;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.markerOptions = new MarkerOptions()
                .position(this.currLocation)
                .title("Status?");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        user = new User();

        //Try to get the changed status from the ProfileFragment.
         //Still NullPointerException


        //status = getArguments().getString("STATUS");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mLoadDataActivity = new LoadDataActivity(getContext(), this);


    }


    @Override
    public void onResume() {
        super.onResume();
        mLoadDataActivity.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoadDataActivity.disconnect();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {


        map = googleMap;



        Toast.makeText(getContext(), "Current Location: " + currLocation, Toast.LENGTH_SHORT).show();

        markerOptions.position(this.currLocation).title("Status?");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));
        map.moveCamera(CameraUpdateFactory.zoomBy((float) 12));

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);
        user.setStatus("Test Status"); // <- insert the real status
        user.updateLocation();



        ArrayList<String[]> arrayListOtherUsers = null;
        String[] arrayString = new String[3];
        arrayString[0] = "PAUL";
        arrayString[1] = "51.2";
        arrayString[2] = "6.2";
        if (arrayListOtherUsers != null) {
            arrayListOtherUsers.add(arrayString);

        for(int i = 0; i< arrayListOtherUsers.size(); i++) {

            String[] array = (String[]) arrayListOtherUsers.get(i);
            LatLng locationOther = new LatLng(Double.parseDouble(array[1]), Double.parseDouble(array[2]));

            //---- getting distance between user location-----
            float[] results = new float[1];
            Location.distanceBetween(currLocation.latitude, currLocation.longitude, locationOther.latitude, locationOther.longitude, results);
            float distance = results[0];
            //---------------------------

            if (distance < 100) { // not ready yet!!! at this point we also need to check "isOnline" and we need to get the Status
                LatLng position = locationOther;
                MarkerOptions marker1 = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
                marker1.position(position);
                map.addMarker(marker1);
            }
        }
        }

       // try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
        /*
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }*/
    }




    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }




    @Override
    public void handleNewLocation(Location location) {


        Toast.makeText(getContext(), "Current Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"))
        this.currLocation = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(this.currLocation).title("Status?");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(this.currLocation));
        map.moveCamera(CameraUpdateFactory.zoomBy((float) 12));

        user.setEmail("testmail@gmail.com");
        user.setIsOnline(true);
        user.setLocation(this.currLocation);

        user.updateLocation();
    }







    public void buttonClicked(){
        //handleNewLocation();
    }




}