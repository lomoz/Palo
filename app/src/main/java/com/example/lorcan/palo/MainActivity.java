package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lorcan.palo.Fragments.OptionsMenu.AboutFragment;
import com.example.lorcan.palo.Fragments.OptionsMenu.ContactFragment;
import com.example.lorcan.palo.Fragments.OptionsMenu.SettingsFragment;
import com.example.lorcan.palo.Fragments.OptionsMenu.ShareFragment;
import com.example.lorcan.palo.Fragments.ProfileFragment;
import com.example.lorcan.palo.GetFromDatabase.GetEncodedImageFromDB;
import com.example.lorcan.palo.GetFromDatabase.GetUsernameFromDB;


import java.util.ArrayList;



/*
 * Add the fragment with the OnFragmentInteractionListener
 * and click on implement method in the error message.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateMapFragment.OnFragmentInteractionListener, ActivityCompat.OnRequestPermissionsResultCallback {

    @SuppressLint("StaticFieldLeak")
    static getLocFromDB locationsFromDB;
    protected static ArrayList<String> arrayListOtherUsers = new ArrayList<>();
    public final int REQUEST_READ_PHONE_STATE = 1;
    public TelephonyManager telephonyManager;
    private String android_id;
    ImageView navImageViewProfile;
    TextView navTextViewUsername;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, CheckForMessageService.class));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationsFromDB = new getLocFromDB(this);
        arrayListOtherUsers = locationsFromDB.getData();

        System.out.println("DATA FROM DB: " + arrayListOtherUsers);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // check whether "READ_PHONE_STATE" permission is granted or not
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // we do not have the permissions, we need to request permission from user
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                return;
        }else{
            android_id = telephonyManager.getDeviceId();
        }




        View hView = navigationView.getHeaderView(0);


        navTextViewUsername = (TextView) hView.findViewById(R.id.navTextViewUsername);
        navImageViewProfile = (ImageView) hView.findViewById(R.id.navImageViewProfile);

        // set username to navigation
        GetUsernameFromDB getUsernameFromDB = new GetUsernameFromDB();
        getUsernameFromDB.getResponseUsername(android_id, this);

        // set profile image to navigation
        GetEncodedImageFromDB getEncodedImageFromDB = new GetEncodedImageFromDB();
        getEncodedImageFromDB.getResponseEncodedImage(android_id, this);

        CurrLocUpdate currLocUpdate = new CurrLocUpdate();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_in_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        currLocUpdate,
                        currLocUpdate.getTag()
                ).commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
      //  CheckForMessageService checkForMessageService = new CheckForMessageService();
      //  checkForMessageService.checkForMessageInit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            System.out.println("CDA onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    protected void onStop() {
        super.onStop();
        //CheckForMessageService checkForMessageService = new CheckForMessageService();
        //checkForMessageService.checkForMessageInit();
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                // if request is cancelled, the result arrays are empty
                if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permissions denied. Show message to user with explanations
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Für deine Android Version ist die Zustimmung für 'PHONE_STATE' nötig. Bei einer Ablehnung wird sich die App leider beenden.");
                    alert.setPositiveButton("Ok", null);
                    alert.show();
                }

                // if we get permissions from user, proceed to
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

                    }
                    android_id = telephonyManager.getDeviceId();
                    // continue with proceed to checkout
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //stopwatch.start();
    }

    @Override
    public void onBackPressed() {
        CurrLocUpdate upFragment = new CurrLocUpdate();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                .replace(R.id.relativelayout_for_fragments,
                        upFragment,
                        upFragment.getTag()
                ).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        /* Out commented the action for the settings in the app header.
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    /*
     * Add actions to happen after click on a menu point item here!
     *
     * If menu icons were added or deleted in activity_main_drawer.xml,
     * the if and else if statements should be added or deleted here as well.
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*
         * TODO:
         * Check which fragment is currently displayed
         * Only change fragment if clicked item differs currently active fragment.
         */

        if (id == R.id.nav_user) {

            /*
             * Create an object of the fragment,
             * use the FragmentManager and call beginTransaction to replace a fragment.
             */

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                    profileFragment,
                    profileFragment.getTag()
            ).commit();
        }

        else if (id == R.id.nav_map) {

            /*
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            */

            CurrLocUpdate currLocUpdate = new CurrLocUpdate();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_in_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            currLocUpdate,
                            currLocUpdate.getTag()
                    ).commit();
        }

        else if (id == R.id.nav_settings) {

            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            settingsFragment,
                            settingsFragment.getTag()
                    ).commit();
        }

        else if (id == R.id.nav_contact) {

            ContactFragment contactFragment = new ContactFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            contactFragment,
                            contactFragment.getTag()
                    ).commit();
        }

        else if (id == R.id.nav_share) {

            ShareFragment shareFragment = new ShareFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            shareFragment,
                            shareFragment.getTag()
                    ).commit();
        }

        else if (id == R.id.nav_about) {

            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                    .replace(R.id.relativelayout_for_fragments,
                            aboutFragment,
                            aboutFragment.getTag()
                    ).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * Method is implemented automatically,
     * after clicking on the according solution of the error message.
     */

    public ArrayList<String> getData(){
        return arrayListOtherUsers;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void setEncodedImageAsNavImage(String image){

        try {
            if(image.length() > 0){
                byte[] decodedString = Base64.decode(image, 0);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                navImageViewProfile.setRotation(90);
                navImageViewProfile.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 64, 64, false));
                decodedByte.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUsernameInNav(String username) {
        navTextViewUsername.setText(username);
    }


}
