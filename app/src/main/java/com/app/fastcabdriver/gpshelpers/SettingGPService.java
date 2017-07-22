package com.app.fastcabdriver.gpshelpers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.app.fastcabdriver.BaseApplication;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.locationrecievers.LocationChangedReceiver;
import com.app.fastcabdriver.locationrecievers.PassiveLocationChangedReceiver;
import com.app.fastcabdriver.utils.PlatformSpecificImplementationFactory;
import com.app.fastcabdriver.utils.base.ILastLocationFinder;
import com.app.fastcabdriver.utils.base.LocationUpdateRequester;


public class SettingGPService {

    private static Context context;

    private static PackageManager packageManager;
    private static LocationManager locationManager;
    private static Criteria criteria;
    private static ILastLocationFinder lastLocationFinder;
    private static LocationUpdateRequester locationUpdateRequester;
    public static PendingIntent locationListenerPendingIntent;
    public static PendingIntent locationListenerPassivePendingIntent;

    private static BasePreferenceHelper prefHelper = new BasePreferenceHelper(BaseApplication.getAppContext());

    public static void settingGPS(Context context, boolean isRunFirst) {

        SettingGPService.context = context;

        // Get references to the managers
        packageManager = context.getPackageManager();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (isRunFirst) {
            // Save that we've been run once.
            prefHelper.setIsFirstRun(true);
        }

        criteria = new Criteria();
        if (GPSConstants.USE_GPS_WHEN_ACTIVITY_VISIBLE) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
        } else {
            criteria.setPowerRequirement(Criteria.POWER_LOW);
        }

        // Setup the location update Pending Intents
        Intent activeIntent = new Intent(context, LocationChangedReceiver.class);
        locationListenerPendingIntent = PendingIntent.getBroadcast(context, 0, activeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent passiveIntent = new Intent(context, PassiveLocationChangedReceiver.class);
        locationListenerPassivePendingIntent = PendingIntent.getBroadcast(context, 0, passiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Instantiate a LastLocationFinder class.
        // This will be used to find the last known location when the application starts.
        lastLocationFinder = PlatformSpecificImplementationFactory.getLastLocationFinder(context);
        lastLocationFinder.setChangedLocationListener(oneShotLastLocationUpdateListener);

        // Instantiate a Location Update Requester class based on the available platform version.
        // This will be used to request location updates.
        locationUpdateRequester = PlatformSpecificImplementationFactory.getLocationUpdateRequester(locationManager);

        try {
            settingIntentService();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void settingIntentService() {
        try {
            prefHelper.setExtraInBackground(false);
            boolean followLocationChanges = true;

            getLocation(followLocationChanges);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static void getLocation(boolean updateWhenLocationChanges) {

        toggleUpdatesWhenLocationChanges(updateWhenLocationChanges);
    }

    protected static void toggleUpdatesWhenLocationChanges(boolean updateWhenLocationChanges) {
        // Save the location update status in shared preferences
        prefHelper.setFollowingLocationChanges(updateWhenLocationChanges);

        // Start or stop listening for location changes
        if (updateWhenLocationChanges) {
            try {
                requestLocationUpdates();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    protected static void requestLocationUpdates() {
        // Normal updates while activity is visible.
        locationUpdateRequester.requestLocationUpdates(GPSConstants.MAX_TIME, GPSConstants.MAX_DISTANCE, criteria, locationListenerPendingIntent);

        // Passive location updates from 3rd party apps when the Activity isn't visible.
        locationUpdateRequester.requestPassiveLocationUpdates(GPSConstants.PASSIVE_MAX_TIME, GPSConstants.PASSIVE_MAX_DISTANCE, locationListenerPassivePendingIntent);

        // Register a receiver that listens for when the provider I'm using has been disabled.
        IntentFilter intentFilter = new IntentFilter(GPSConstants.ACTIVE_LOCATION_UPDATE_PROVIDER_DISABLED);
        if( CheckGPSAvailibility.checkGPSAvailibility((Activity) context)) {
            context.registerReceiver(locProviderDisabledReceiver, intentFilter);
        }

        // Register a receiver that listens for when a better provider than I'm using becomes available.
        String bestProvider = locationManager.getBestProvider(criteria, false);
        String bestAvailableProvider = locationManager.getBestProvider(criteria, true);
        if (bestProvider != null && !bestProvider.equals(bestAvailableProvider)) {
            locationManager.requestLocationUpdates(bestProvider, 0, 0, bestInactiveLocationProviderListener, context.getMainLooper());
        }
    }

    protected static LocationListener oneShotLastLocationUpdateListener = new LocationListener() {
        public void onLocationChanged(Location l) {

        }

        public void onProviderDisabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }
    };

    protected static BroadcastReceiver locProviderDisabledReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean providerDisabled = !intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
            // Re-register the location listeners using the best available Location Provider.
            if (providerDisabled) {
                try {
                    requestLocationUpdates();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    protected static LocationListener bestInactiveLocationProviderListener = new LocationListener() {
        public void onLocationChanged(Location l) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
            // Re-register the location listeners using the better Location Provider.
            requestLocationUpdates();
        }
    };


}
