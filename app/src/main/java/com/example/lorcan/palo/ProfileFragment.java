package com.example.lorcan.palo;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Bitmap.createBitmap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

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

    EditText etStatus, etJob;
    Button btnChange;
    Spinner spinner;

    private final String TAG = getClass().getName();

    ImageView ivCamera, ivGallery, ivImage;
    FloatingActionButton fabUpload;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 1;
    final int GALLERY_REQUEST = 2;
    String selectedPhoto;

    ArrayList<String> spinnerArray = new ArrayList<>();

    String status = "";
    String job = "";

    Bitmap bitmapProfileImage;

    private String android_id;
    public String time;
    public Double lat;
    public Double lng;

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create and return a new View element here.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        cameraPhoto = new CameraPhoto(this.getActivity());
        galleryPhoto = new GalleryPhoto(this.getActivity());

        ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        fabUpload = (FloatingActionButton) view.findViewById(R.id.fabUpload);

        // Use the created view to get the elements from the xml file.
        etStatus = (EditText) view.findViewById(R.id.etStatus);
        etJob = (EditText) view.findViewById(R.id.etStudyCourse);
        btnChange = (Button) view.findViewById(R.id.btnChangeInMap);

        TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        if (tManager != null) {
            android_id = tManager.getDeviceId();
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        time = dateFormat.format(date);

        getStatusFromDB get = new getStatusFromDB();
        get.getStatus(android_id, this);

        GetEncodedImageFromDB getEncodedImageFromDB = new GetEncodedImageFromDB();
        getEncodedImageFromDB.getResponseEncodedImage(android_id, this);


        /*
         * Read ArrayList from File.
         */

        String filename = "user_status";
        FileManager fileManager = new FileManager();
        spinnerArray = fileManager.readFromFile(getContext(), filename);

        if (bitmapProfileImage != null) {

            ivImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapProfileImage, 256, 256, false));
        }
        else {
            Toast.makeText(ProfileFragment.this.getActivity(), "Something went wrong while loading the profile image.", Toast.LENGTH_SHORT).show();
        }


        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Something wrong while taking photos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedPhoto == null || selectedPhoto.equals("")) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "No image selected.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(128, 128).getBitmap();
                    String encodedImage = ImageBase64.encode(bitmap);
                    Log.d(TAG, encodedImage);

                    SendEncodedImageToDB sendEncodedImageToDB = new SendEncodedImageToDB();
                    sendEncodedImageToDB.sendEncodedImage(encodedImage);

                    Toast.makeText(ProfileFragment.this.getActivity(), "Image has been uploaded.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Something wrong while encoding photos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        spinnerArray.add("Item 1");
        spinnerArray.add("Item 2");
        spinnerArray.add("Item 3");

        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // if any item is selected this one should become the active status
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // if no item is selected the last used status should stay the active status
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    //ivImage.setRotation(90);
                    ivImage.setImageBitmap(getRotatedBitmap(bitmap));
                } catch (FileNotFoundException e) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Something wrong while loading photos.", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    ivImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Something wrong while choosing photos.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void setStatusToEditText(String status){
        etStatus.setText(status);
    }

    public void setEncodedImageAsImageView(String image){

        System.out.println(image);
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //bitmapProfileImage = ImageBase64.decode(image);
        //Toast.makeText(ProfileFragment.this.getActivity(), "Something went wrong while loading the profile image.", Toast.LENGTH_SHORT).show();
        //bitmapProfileImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_profile_picture);
        ivImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 256, 256, false));


    }
    public void btnChangeClicked() {

        if (etStatus.getText().toString().isEmpty() && etJob.getText().toString().isEmpty()) {


            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
            builder.setTitle(R.string.alert_empty_status_and_job_title);
            builder.setMessage(R.string.alert_empty_status_and_job_message);
            builder.show();

        }

        else if (etStatus.getText().toString().isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
            builder.setTitle(R.string.alert_empty_status_title);
            builder.setMessage(R.string.alert_empty_status_message);
            builder.show();
        }

        else if (etJob.getText().toString().isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
            builder.setTitle(R.string.alert_empty_job_title);
            builder.setMessage(R.string.alert_empty_job_message);
            builder.show();
        }

        else {

            status = etStatus.getText().toString();
            job = etJob.getText().toString();

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
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
                            sendStatusToDB statusToDB = new sendStatusToDB();
                            statusToDB.sendStatus(etStatus.getText().toString(), location.getLatitude(), location.getLongitude(), time, android_id);
                            CurrLocUpdate upFragment = new CurrLocUpdate();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                                    .replace(R.id.relativelayout_for_fragments,
                                            upFragment,
                                            upFragment.getTag()
                                    ).commit();
                        }
                    });
        }
    }

    @SuppressLint("HardwareIds")
    public void startMapAndUploadStatus() {

        TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (tManager != null) {
            android_id = tManager.getDeviceId();
        }

        CurrLocUpdate mapFragment = new CurrLocUpdate();

        //bundle the data from status and study course to "send" them to MapFragment.java


        //send status to database
        sendStatusToDB statusToDB = new sendStatusToDB();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        time = dateFormat.format(date);
        statusToDB.sendStatus(status, lat, lng, time, android_id);


        /*
         * Write user status to internal storage.
         */

        String filename = "user_status";
        FileManager fileManager = new FileManager();
        fileManager.writeToFile(getContext(), filename, status);


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        mapFragment,
                        mapFragment.getTag()
                ).commit();
    }

    private Bitmap getRotatedBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) 90);
        return createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}