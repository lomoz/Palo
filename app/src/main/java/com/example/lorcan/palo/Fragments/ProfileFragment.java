package com.example.lorcan.palo.Fragments;


import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lorcan.palo.CurrLocUpdate;
import com.example.lorcan.palo.GetFromDatabase.GetEncodedImageFromDB;
import com.example.lorcan.palo.GetFromDatabase.GetStatusFromDB;
import com.example.lorcan.palo.GetFromDatabase.GetUsernameFromDB;
import com.example.lorcan.palo.MarkerColorJSON;
import com.example.lorcan.palo.MyApplicationContext;
import com.example.lorcan.palo.OldStatus;
import com.example.lorcan.palo.OnClickSendToDB;
import com.example.lorcan.palo.ProfilActivity;
import com.example.lorcan.palo.R;
import com.example.lorcan.palo.SendEncodedImageToDB;
import com.example.lorcan.palo.UsernameJSON;
import com.example.lorcan.palo.sendStatusToDB;
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
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Bitmap.createBitmap;
import static android.graphics.Bitmap.createScaledBitmap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ProfileFragment(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
        startMapAndUploadStatus();
    }

    public int REQUEST_CHECK_SETTINGS = 0x1;
    public final int PERMISSION_ACCESS_FINE_LOCATION = 2;
    public final int PERMISSION_ACCESS_COARSE_LOCATION = 3;
    private Bitmap bitmap;
    public int marker;
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            String blockCharacterSet = "\\";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    /*
     * Declare elements here to handle them in the onCreateView method.
     */

    TextView tvUsername;
    EditText etStatus;
    Button btnChange;
    Spinner spinner;
    RelativeLayout background;
    ImageButton fab_marker1;
    ImageButton fab_marker2, fab_marker3, fab_marker4, fab_marker5, fab_marker6, fab_marker7, fab_marker8, fab_marker9, fab_marker10;
    BitmapDrawable bitmapDrawableSelectedMarkerColor;
    Bitmap bitmapSelectedMarkerColor;

    private final String TAG = getClass().getName();

    File file;
    Uri uri;
    Intent CameraIntent, GalleryIntent, CropIntent;
    final int PERMISSION_CAMERA_CODE = 1;
    Bitmap croppedBitmap;
    Bitmap rotatedBitmap;
    Bitmap decodedByte;

    ImageView ivImage;

    FloatingActionButton fabImageDialog;
    FloatingActionButton iconListBtn;

    final int CAMERA_REQUEST = 1;
    final int GALLERY_REQUEST = 2;
    String selectedPhoto;

    ArrayList<String> spinnerArray = new ArrayList<>();
    String chose_status = "";

    String status = "";

    private String android_id;
    public String time;
    public Double lat;
    public Double lng;

    int cnt_profile_image;
    int cnt_status_profile;
    int cnt_marker_color;

    @SuppressLint({"HardwareIds", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create and return a new View element here.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        background = (RelativeLayout) view.findViewById(R.id.relLayProfileFrag);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        fabImageDialog = (FloatingActionButton) view.findViewById(R.id.fabImageDialog);
        iconListBtn = (FloatingActionButton) view.findViewById(R.id.iconList);

        // Use the created view to get the elements from the xml file.
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        etStatus = (EditText) view.findViewById(R.id.etStatus);
        etStatus.setFilters(new InputFilter[]{filter});
        btnChange = (Button) view.findViewById(R.id.btnChangeInMap);



        fab_marker1 = (ImageButton) view.findViewById(R.id.fab_marker1);
        fab_marker2 = (ImageButton) view.findViewById(R.id.fab_marker2);
        fab_marker3 = (ImageButton) view.findViewById(R.id.fab_marker3);
        fab_marker4 = (ImageButton) view.findViewById(R.id.fab_marker4);
        fab_marker5 = (ImageButton) view.findViewById(R.id.fab_marker5);
        fab_marker6 = (ImageButton) view.findViewById(R.id.fab_marker6);
        fab_marker7 = (ImageButton) view.findViewById(R.id.fab_marker7);
        fab_marker8 = (ImageButton) view.findViewById(R.id.fab_marker8);
        fab_marker9 = (ImageButton) view.findViewById(R.id.fab_marker9);
        fab_marker10 = (ImageButton) view.findViewById(R.id.fab_marker10);

        int permissionCheck = ContextCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            RequestRuntimePermission();
        }

        TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }

        if (tManager != null) {
            android_id = tManager.getDeviceId();
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        time = dateFormat.format(date);

        UsernameJSON usernameJSON = new UsernameJSON();
        final String name = usernameJSON.getUserName();
        tvUsername.setText(name);

        // Receive status from database.
        GetStatusFromDB getStatusFromDB = new GetStatusFromDB();
        getStatusFromDB.getStatus(android_id, this, etStatus);

        final GetEncodedImageFromDB getEncodedImageFromDB = new GetEncodedImageFromDB();
        getEncodedImageFromDB.getResponseEncodedImage(android_id, this);


        iconListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplicationContext.getAppContext(), ProfilActivity.class);

                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        fabImageDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
                builder.setTitle(R.string.alert_upload_image_title);
                builder.setMessage(R.string.alert_upload_image_message);

                // Select Camera
                builder.setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CameraOpen();
                    }
                });

                // Select Gallery
                builder.setNegativeButton(R.string.gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GalleryOpen();
                    }
                });

                builder.show();
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

        Locale current = getResources().getConfiguration().locale;
        final String language = current.getLanguage();

        switch (language) {
            case "de":
                chose_status = "--- Wähle Status ---";
                break;

            case "en":
                chose_status = "--- Chose Status --- ";
                break;
        }

        spinnerArray.add(chose_status);

        try {
            String oldUserStatus = OldStatus.getData(MyApplicationContext.getAppContext());
            System.out.println("JSON STATUS PROFILE FRAGMENT: " + oldUserStatus);
            JSONObject jsonObject = new JSONObject(oldUserStatus);
            JSONArray jsonArray = jsonObject.getJSONArray("Status");
            for (int i = 1; i < jsonArray.length(); i++) {
                spinnerArray.add(jsonArray.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e1) {
            e1.printStackTrace();

        }

        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // if any item is selected this one should become the active status
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                if (!selectedItemText.equals(chose_status)) {
                    Toast.makeText(ProfileFragment.this.getActivity(), selectedItemText, Toast.LENGTH_SHORT).show();
                    etStatus.setText(selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // if no item is selected the last used status should stay the active status
            }
        });
        final MarkerColorJSON markerColorJSON = new MarkerColorJSON();

        fab_marker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker1);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker1));
                marker = 1;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker1);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker2);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker2));
                marker = 2;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker2);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker3);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker3));
                marker = 3;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker3);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker4);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker4));
                marker = 4;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker4);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker5);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker5));
                marker = 5;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker5);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker6);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker6));
                marker = 6;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker6);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker7);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker7));
                marker = 7;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker7);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker8);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker8));
                marker = 8;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker8);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker9);
                background.setBackgroundColor(getResources().getColor(R.color.color_marker9));
                marker = 9;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker9);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

        fab_marker10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer color = getResources().getColor(R.color.color_marker10);
                marker = 10;
                markerColorJSON.setActColor(marker);
                bitmapDrawableSelectedMarkerColor = (BitmapDrawable) getResources().getDrawable(R.drawable.marker10);
                bitmapSelectedMarkerColor = createScaledBitmap(bitmapDrawableSelectedMarkerColor.getBitmap(), 170, 125, false);
                cnt_marker_color += 1;
                System.out.println(background.getBackground());
                ValueAnimator valueAnimator = ValueAnimator.ofArgb(background.getDrawingCacheBackgroundColor(),color);
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == RESULT_OK) {
            CropImage();
        } else if (requestCode == 2) {
            if (data != null) {
                uri = data.getData();
                selectedPhoto = uri.getPath();
                CropImage();
            }
        } else if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    croppedBitmap = bundle.getParcelable("data");
                }
                rotatedBitmap = getRotatedBitmap(croppedBitmap);
                croppedBitmap.recycle();
                ivImage.setImageBitmap(Bitmap.createScaledBitmap(rotatedBitmap, 200, 200, false));

                // Save cropped image to external storage and get Path afterwards to upload to DB
                Uri croppedUri = saveOutput(rotatedBitmap);
                selectedPhoto = croppedUri.getPath();

                if (selectedPhoto != null) {
                    uploadImage(selectedPhoto);
                }
            }
        }
    }

    public void setEncodedImageAsProfileImage(String image) {

        try {
            if (image.length() > 0) {
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                if (decodedByte != null) {
                    decodedByte.recycle();
                    decodedByte = null;
                }
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivImage.setRotation(90);
                ivImage.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 200, 200, false));
                decodedByte.recycle();
                decodedByte = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnChangeClicked() {

        System.out.println("btnChange Clicked");
        OnClickSendToDB onClickSendToDB = new OnClickSendToDB();
        onClickSendToDB.sendBtnClick(android_id, "1");

        if (etStatus.getText().toString().isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
            builder.setTitle(R.string.alert_empty_status_title);
            builder.setMessage(R.string.alert_empty_status_message);
            builder.show();
        }

        else {

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApplicationContext.getAppContext());
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                ActivityCompat.requestPermissions(ProfileFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);

                status = etStatus.getText().toString();
                return;
            }

            status = etStatus.getText().toString();
            etStatus.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if(location != null){
                                System.out.println("Location in ProfileFragment: " + location.toString());
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                                sendStatusToDB statusToDB = new sendStatusToDB();
                                statusToDB.sendStatus(etStatus.getText().toString(), lat, lng, time, android_id, marker);
                                CurrLocUpdate upFragment = new CurrLocUpdate();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                                        .replace(R.id.relativelayout_for_fragments,
                                                upFragment,
                                                upFragment.getTag()
                                        ).commit();
                            }

                            else {
                                displayLocationSettingsRequest(MyApplicationContext.getAppContext());
                            }
                        }
                    });
        }
    }

    @SuppressLint("HardwareIds")
    public void startMapAndUploadStatus() {

        TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (tManager != null) {
            android_id = tManager.getDeviceId();
        }

        //bundle the data from status and study course to "send" them to MapFragment.java
        OldStatus oldList = new OldStatus();
        oldList.addNewEntry(status);
        //send status to database
        sendStatusToDB statusToDB = new sendStatusToDB();
        cnt_status_profile += 1;

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy");
        Date date = new Date();
        time = dateFormat.format(date);
        statusToDB.sendStatus(status, lat, lng, time, android_id, marker);

        CurrLocUpdate mapFragment = new CurrLocUpdate();
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
        matrix.postRotate((float) -90);
        return createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void uploadImage(String selectedPhoto) {

        if (selectedPhoto == null || selectedPhoto.equals("")) {
            Toast.makeText(ProfileFragment.this.getActivity(), R.string.no_image_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            bitmap = ImageLoader.init().from(selectedPhoto).requestSize(128, 128).getBitmap();
            String encodedImage = ImageBase64.encode(bitmap);
            bitmap.recycle();
            bitmap = null;
            Log.d(TAG, encodedImage);

            SendEncodedImageToDB sendEncodedImageToDB = new SendEncodedImageToDB();
            sendEncodedImageToDB.sendEncodedImage(encodedImage);

            OnClickSendToDB onClickSendToDB = new OnClickSendToDB();
            onClickSendToDB.sendBtnClick(android_id, "6");

            cnt_profile_image += 1;

            Toast.makeText(ProfileFragment.this.getActivity(), R.string.image_upload_success, Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(ProfileFragment.this.getActivity(), R.string.image_upload_denied, Toast.LENGTH_SHORT).show();
        }
    }

    private void RequestRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileFragment.this.getActivity(), android.Manifest.permission.CAMERA)) {
            Toast.makeText(ProfileFragment.this.getActivity(), "CAMERA permission allows us to access CAMERA app.", Toast.LENGTH_SHORT).show();
        }

        else {
            ActivityCompat.requestPermissions(ProfileFragment.this.getActivity(), new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
        }
    }

    private void CameraOpen() {
        try {
            CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = new File(Environment.getExternalStorageDirectory(),
                    "file" + String.valueOf(System.currentTimeMillis()) + ".jpeg");

            uri = Uri.fromFile(file);
            CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            CameraIntent.putExtra("return-data", true);
            startActivityForResult(CameraIntent, 0);
        }catch(Exception e){
            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this.getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this.getActivity());
            }
            builder.setIcon(R.drawable.ic_menu_camera)
                    .setTitle("Kameraproblem")
                    .setMessage("Hallo " + tvUsername.getText()+", \nleider haben wir derzeit bei den neusten Android-Versionen noch ein Problem mit Bildern aus der Kamera. Bitte nutze, um ein Profilbild einzustellen, deine Galerie. Wir arbeiten an dem Problem und versuchen es so schnell es möglich ist zu lösen. \nVielen Dank für dein Verständnis \n dein Palo-Team. :)")
                    .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
            e.printStackTrace();
            System.out.println(uri);
        }
    }

    private void GalleryOpen() {

        GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalleryIntent, "Select Image from Gallery"), 2);
    }

    private void CropImage() {

        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_ACCESS_COARSE_LOCATION: {

                btnChangeClicked();
            }

            case PERMISSION_ACCESS_FINE_LOCATION:
            {



            }
        }
    }

    private Uri saveOutput(Bitmap croppedImage) {
        Uri saveUri = null;
        File file = new File(Environment.getExternalStorageDirectory(),"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        OutputStream outputStream;

        try {
            file.getParentFile().mkdirs();
            saveUri = Uri.fromFile(file);
            outputStream = MyApplicationContext.getAppContext().getContentResolver().openOutputStream(saveUri);
            if (outputStream != null) {
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  saveUri;
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
}