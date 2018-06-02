package de.app.classic.palo;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


// Get Location and Data From DB
public class LoadDataActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    public interface LocationCallback {
        void handleNewLocation(Location location);
    }


    private static final String TAG = LocationProvider.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationCallback mLocationCallback;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationListener mLocationlistener;

    public LoadDataActivity(Context context, LocationCallback callback) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationCallback = callback;

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds

        mContext = context;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplicationContext.getAppContext());

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location == null) {
                            if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationlistener);
                        }
                        else {
                            mLocationCallback.handleNewLocation(location);
                        }

                    }
                });



        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
            if (connectionResult.hasResolution() && mContext instanceof Activity) {
                try {
                    Activity activity = (Activity)mContext;
                    // Start an Activity that tries to resolve the error
                    connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
                } catch (IntentSender.SendIntentException e) {
                    // Log the error
                    e.printStackTrace();
                }
            } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
                Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            mLocationCallback.handleNewLocation(location);
        }
    }

