package com.example.lorcan.palo;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FusedLocationProviderClient mFusedLocationClient;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public ProfileFragment(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
        startMapAndUploadStatus();
    }

    /*
     * Declare elements here to handle them in the onCreateView method.
     */

    EditText etStatus, etStudyCourse;
    Button btnChange;

    String status = "";
    String studyCourse = "";

    private String android_id;
    public String time;
    public Double lat;
    public Double lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getStatusFromDB get = new getStatusFromDB();
        status = get.getStatus(android_id);

        // Create and return a new View element here.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Use the created view to get the elements from the xml file.
        etStatus = (EditText)view.findViewById(R.id.etStatus);
        etStudyCourse = (EditText)view.findViewById(R.id.etStudyCourse);
        btnChange = (Button)view.findViewById(R.id.btnChangeInMap);

        etStatus.setText(status);

        /*
         * Create an onClickListener for the button.
         *
         * To make i.e. a correct Toast it's important to replace "this"
         * with "ProfileFragment.this.getActivity()"!
         */

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChangeClicked();
            }
        });

        return view;
    }



    public void btnChangeClicked() {


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        MapFragment mapFragment = new MapFragment();
                        Bundle bundleLocation = new Bundle();

                        if (location != null) {
                            // Logic to handle location object
                            System.out.println("*************************" + location + "*************************");

                            lat = location.getLatitude();
                            lng = location.getLongitude();

                            mapFragment.setArguments(bundleLocation);

                        }
                        startMapAndUploadStatus();
                    }
                });
    }

    public void startMapAndUploadStatus(){

        TelephonyManager tManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        android_id = tManager.getDeviceId();

        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();

        //check if editText status is empty or not.
        if (etStatus.getText().toString().isEmpty()) {
            status = getString(R.string.status_empty);
            Toast.makeText(ProfileFragment.this.getActivity(), getString(R.string.status_empty), Toast.LENGTH_SHORT).show();
        }
        else {
            status = etStatus.getText().toString();
            Toast.makeText(ProfileFragment.this.getActivity(), status, Toast.LENGTH_SHORT).show();
        }

        //check if editText studyCourse is empty or not.
        if (etStudyCourse.getText().toString().isEmpty()) {
            studyCourse = getString(R.string.empty_job);
            Toast.makeText(ProfileFragment.this.getActivity(), getString(R.string.empty_job), Toast.LENGTH_SHORT).show();
        }
        else {
            studyCourse = etStudyCourse.getText().toString();
            Toast.makeText(ProfileFragment.this.getActivity(), studyCourse, Toast.LENGTH_SHORT).show();
        }

        //bundle the data from status and study course to "send" them to MapFragment.java
        bundle.putString("status", status);
        bundle.putString("study course", studyCourse);

        //send status to database
        sendStatusToDB statusToDB = new sendStatusToDB();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        time = dateFormat.format(date);
        statusToDB.sendStatus("testmail@gmail.com", status, lat, lng, time, android_id, getContext());

        mapFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        mapFragment,
                        mapFragment.getTag()
                ).commit();
    }
}
