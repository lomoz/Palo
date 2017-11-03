package com.example.lorcan.palo;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrLocUpdate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrLocUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrLocUpdate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CurrLocUpdate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrLocUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrLocUpdate newInstance(String param1, String param2) {
        CurrLocUpdate fragment = new CurrLocUpdate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new MyAsyncTask(getContext()).execute();
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> implements LocationListener {
        private Context ContextAsync;

        public MyAsyncTask(Context context) {
            this.ContextAsync = context;
        }

        Dialog progress;
        private String providerAsync;
        private LocationManager locationManagerAsync;
        double latAsync = 0.0;
        double lonAsync = 0.0;
        String thikanaAsync = "Scan sms for location";

        String AddressAsync = "";
        Geocoder GeocoderAsync;

        Location location;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(ContextAsync, "Loading data", "Please wait...");

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            locationManagerAsync = (LocationManager) ContextAsync.getSystemService(ContextAsync.LOCATION_SERVICE);


            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setCostAllowed(false);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
            providerAsync = locationManagerAsync.getBestProvider(criteria, false);


            if (locationManagerAsync.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                providerAsync = LocationManager.GPS_PROVIDER;
            } else if (locationManagerAsync.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                providerAsync = LocationManager.NETWORK_PROVIDER;
            /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GPS is disabled in the settings!");
            alert.setMessage("It is recomended that you turn on your device's GPS and restart the app so the app can determine your location more accurately!");
            alert.setPositiveButton("OK", null);
            alert.show();*/
            } else if (locationManagerAsync.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                providerAsync = LocationManager.PASSIVE_PROVIDER;
                //Toast.makeText(ContextAsync, "Switch On Data Connection!!!!", Toast.LENGTH_LONG).show();
            }

            if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            location = locationManagerAsync.getLastKnownLocation(providerAsync);
            // Initialize the location fields
            if (location != null) {
                //  System.out.println("Provider " + provider + " has been selected.");
                latAsync = location.getLatitude();
                lonAsync = location.getLongitude();

            } else {
                //Toast.makeText(ContextAsync, " Locationnot available", Toast.LENGTH_SHORT).show();
            }


            List<Address> addresses = null;
            GeocoderAsync = new Geocoder(ContextAsync, Locale.getDefault());
            try {
                addresses = GeocoderAsync.getFromLocation(latAsync, lonAsync, 1);

                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getCountryName();
                AddressAsync = Html.fromHtml(
                        address + ", " + city + ",<br>" + country).toString();
            } catch (Exception e) {
                e.printStackTrace();
                AddressAsync = "Refresh for the address";
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            progress.dismiss();
            onLocationChanged(location);
            System.out.println("latAsync_lonAsync: " + latAsync + "_" + lonAsync);
            double[] latlng = new double[2];
            latlng[0] = latAsync;
            latlng[1] = lonAsync;
            Bundle bundle = new Bundle();
            bundle.putDoubleArray("latlng", latlng);
            MapFragment mapFragment = new MapFragment();
            mapFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            mapFragment,
                            mapFragment.getTag()
                    ).commit();

        }


        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManagerAsync.requestLocationUpdates(providerAsync, 0, 0, this);
        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("Mach GPS an!");
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }

    /*
                bundle.putDoubleArray("latlng", latlng);
            mapFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            mapFragment,
                            mapFragment.getTag()
                    ).commit();
     */
}