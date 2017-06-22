package com.example.lorcan.palo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/*
 * Add the fragment with the OnFragmentInteractionListener
 * and click on implement method in the error message.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TwoCheckedFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        /*
         * Changed code generated method setDrawerListener to addDrawerListener.
         */

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
         * Set a fragment as the default fragment instead of an empty fragment.
         */

        TwoCheckedFragment twoCheckedFragment = TwoCheckedFragment.newInstance(10);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(
                R.id.relativelayout_for_fragments,
                twoCheckedFragment,
                twoCheckedFragment.getTag()
        ).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            /*
             * Create an object of the fragment,
             * use the FragmentManager and call beginTransaction to replace a fragment.
             */

            UncheckedFragment uncheckedFragment = new UncheckedFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(
                    R.id.relativelayout_for_fragments,
                    uncheckedFragment,
                    uncheckedFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_gallery) {

            /*
             * Call a Fragment with the newInstance method like this.
             * Only to pass data from outside to the fragment.
             */

            OneCheckedFragment oneCheckedFragment = OneCheckedFragment.newInstance("some1", "some2");
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(
                    R.id.relativelayout_for_fragments,
                    oneCheckedFragment,
                    oneCheckedFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            /*
             * Get data from the fragment with an interactionListener to the outside!
             * Recommended way to use a fragment from android!
             */

            TwoCheckedFragment twoCheckedFragment = TwoCheckedFragment.newInstance(10);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(
                    R.id.relativelayout_for_fragments,
                    twoCheckedFragment,
                    twoCheckedFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * Method is implemented automatically,
     * after clicking on the according solution of the error message.
     */

    @Override
    public void onFragmentInteraction(String data) {

        /*
         * i.e. make a toast to show the data.
         */

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
