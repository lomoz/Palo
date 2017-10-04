package com.example.lorcan.palo;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    /*
     * Declare elements here to handle them in the onCreateView method.
     */

    EditText etStatus, etStudyCourse;
    Button btnOK;

    String status = "";
    String studyCourse = "";

    private String android_id;
    public String time;
    public Double lat;
    public Double lng;
    public LocationListener listener;
    public LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();

        getStatusFromDB get = new getStatusFromDB();
        status = get.getStatus(android_id);

        // Create and return a new View element here.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Use the created view to get the elements from the xml file.
        etStatus = (EditText)view.findViewById(R.id.etStatus);
        etStudyCourse = (EditText)view.findViewById(R.id.etStudyCourse);
        btnOK = (Button)view.findViewById(R.id.btnOK);

        etStatus.setText(status);

        /*
         * Create an onClickListener for the button.
         *
         * To make i.e. a correct Toast it's important to replace "this"
         * with "ProfileFragment.this.getActivity()"!
         */

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
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
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
    }
}
