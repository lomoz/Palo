package com.example.lorcan.palo;



import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrLocUpdate extends Fragment  {

    public FusedLocationProviderClient mFusedLocationClient;

    public CurrLocUpdate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplicationContext.getAppContext());

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        UpdateMapFragment update = new UpdateMapFragment();
                        Bundle bundle = new Bundle();

                            // Logic to handle location object
                            System.out.println("*************************" + location + "*************************");

                            double[] currLoc = new double[2];
                            currLoc[0] = location.getLatitude();
                            currLoc[1] = location.getLongitude();
                            bundle.putDoubleArray("currLoc", currLoc);
                            System.out.println("CURRENT LOCATION CurrLocUpdate: " + currLoc[0]);
                            update.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                                    .replace(R.id.relativelayout_for_fragments,
                                            update,
                                            update.getTag()
                                    ).commit();


                    }
                });

        return inflater.inflate(R.layout.fragment_curr_loc_update, container, false);
    }

}
