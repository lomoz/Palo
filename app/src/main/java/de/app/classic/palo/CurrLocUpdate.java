package de.app.classic.palo;



import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.app.classic.palo.Fragments.ProfileFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;

import de.app.classic.palo.R;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrLocUpdate extends Fragment  {

    public FusedLocationProviderClient mFusedLocationClient;

    public CurrLocUpdate() {
        // Required empty public constructor
    }

    public int REQUEST_CHECK_SETTINGS = 0x1;

    public final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    public final int PERMISSION_ACCESS_COARSE_LOCATION = 2;
    public double lat;
    public double lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);

            return null;
        }else{
            locate();
        }


        return inflater.inflate(R.layout.fragment_curr_loc_update, container, false);
    }


    private void locate() {

        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            builder.setTitle("GPS anschalten")
                    .setMessage("Bitte schalte dein GPS an, damit Palo reibungslos funktioniert.")
                    .setCancelable(false)
                    .setPositiveButton("GPS AN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            getActivity().startActivity(callGPSSettingIntent);
                            locate();
                        }
                    });
            builder.show();
        }else{
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);
            }else {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplicationContext.getAppContext());
                final CurrLocUpdate currLocUpdate = this;
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {

                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location == null) {
                                    if (ActivityCompat.checkSelfPermission(currLocUpdate.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(currLocUpdate.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(currLocUpdate.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                                        ActivityCompat.requestPermissions(currLocUpdate.getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);
                                    } else {
                                        mFusedLocationClient.getLastLocation()
                                                .addOnSuccessListener(currLocUpdate.getActivity(), new OnSuccessListener<Location>() {
                                                    @Override
                                                    public void onSuccess(Location location) {
                                                        System.out.println("location: " + location);
                                                    }
                                                });
                                    }
                                }

                                UpdateMapFragment update = UpdateMapFragment.newInstance("1", "2");
                                Bundle bundle = new Bundle();

                                // Logic to handle location object
                                System.out.println("*************************" + location + "*************************");

                                double[] currLoc = new double[2];
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();


                                    currLoc[0] = lat;
                                    currLoc[1] = lng;
                                    bundle.putDoubleArray("currLoc", currLoc);
                                    update.setArguments(bundle);

                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                                            .replace(R.id.relativelayout_for_fragments,
                                                    update,
                                                    update.getTag()
                                            ).commitAllowingStateLoss();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CurrLocUpdate.this.getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                                    builder.setTitle("Kein Standort gefunden");
                                    builder.setMessage("Es wurde leider kein Standort gefunden. Möchtest du es erneut probieren?");

                                    // Select Camera
                                    builder.setPositiveButton("Erneut versuchen", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if(CurrLocUpdate.this.getActivity() != null) {
                                                if (Build.VERSION.SDK_INT >= 11) {
                                                    CurrLocUpdate.this.getActivity().recreate();
                                                } else {
                                                    CurrLocUpdate.this.getActivity().finish();
                                                    CurrLocUpdate.this.getActivity().startActivity(CurrLocUpdate.this.getActivity().getIntent());
                                                }
                                            }
                                        }
                                    });

                                    builder.setNeutralButton("Profil", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            ProfileFragment profileFragment = new ProfileFragment();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                                                    .replace(R.id.relativelayout_for_fragments,
                                                            profileFragment,
                                                            profileFragment.getTag()
                                                    ).commit();

                                        }
                                    });

                                    builder.show();


                                    //open settings to activate GPS
                                    //displayLocationSettingsRequest(MyApplicationContext.getAppContext());
                                }


                            }
                        });
            }
        }
    }
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_ACCESS_COARSE_LOCATION: {
                if (Build.VERSION.SDK_INT >= 11) {
                    this.getActivity().recreate();
                } else {
                    this.getActivity().finish();
                    this.getActivity().startActivity(this.getActivity().getIntent());
                }
            }

            case PERMISSION_ACCESS_FINE_LOCATION: {
                if (Build.VERSION.SDK_INT >= 11) {
                    this.getActivity().recreate();
                } else {
                    this.getActivity().finish();
                    this.getActivity().startActivity(this.getActivity().getIntent());
                }
            }
        }
    }

}
