/*
 *	Copyright (c) 2017 by Contributors of the BIG IoT Project Consortium (see below).
 *	All rights reserved.
 *
 *	This source code is licensed under the MIT license found in the
 * 	LICENSE file in the root directory of this source tree.
 *
 *	Contributor:
 *	- Robert Bosch GmbH
 *	    > Stefan Schmid (stefan.schmid@bosch.com)
 */
package android.bigiot.org.androidexampleconsumer;

import android.bigiot.org.androidexampleconsumer.controller.BigIotController;
import android.bigiot.org.androidexampleconsumer.model.RouteInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.bigiot.org.androidexampleconsumer.controller.GoogleRouteController;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity implements GoogleRouteController.RouteResolvedCallback{

    private GoogleRouteController.RouteResolvedCallback routeResolvedCallback;
    private Tracker mTracker;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            String selectedTag = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.newInstance();
                    selectedTag = HomeFragment.TAG;
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = DashboardFragment.newInstance();
                    selectedTag = DashboardFragment.TAG;
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = NotificationsFragment.newInstance();
                    selectedTag = NotificationsFragment.TAG;
                    break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, selectedTag).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_home);

        routeResolvedCallback = this;

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        try {
            //BigIotController.getInstance(this).accessOffering();
            //BigIotController.getInstance(this).accessOffering();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Location originLocation = new Location("pp");
        originLocation.setLatitude(41.1);
        originLocation.setLongitude(2.1);

        Location destinationLocation = new Location("pp");
        destinationLocation.setLatitude(41.5);
        destinationLocation.setLongitude(2.13);

        GoogleRouteController.routeRequest(this, originLocation, destinationLocation, this);

        /*
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
                */
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Destination")
                .setAction("Access")
                .setLabel(destinationLocation.getLatitude() + " " + destinationLocation.getLongitude())
                .build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onRouteResolved(RouteInfo distanceProperties) {
        Log.e("TAG", distanceProperties.toString());
        Log.e("TAG", distanceProperties.getDistance() + "");
        Log.e("TAG", distanceProperties.getTime() + "");
    }
}